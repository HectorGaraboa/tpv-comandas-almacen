package com.example.comandero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import com.example.comandero.model.Categoria;
import com.example.comandero.model.Producto;
import com.example.comandero.model.dto.ComandaRequest;
import com.example.comandero.offline.PendingComandaStore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvHeader;
    private RecyclerView rvCategorias;
    private RecyclerView rvProductos;
    private Button btnOpciones;
    private Button btnEnviar;

    private long mesaId;
    private String mesaNombre;
    private String camareroNombre;

    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Producto> productos = new ArrayList<>();
    private final Map<Long, Integer> seleccion = new HashMap<>();
    private final Map<Long, Producto> productosIndexados = new HashMap<>();

    private CategoriaAdapter categoriaAdapter;
    private ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        btnBack = findViewById(R.id.btnBack);
        tvHeader = findViewById(R.id.tvHeader);
        rvCategorias = findViewById(R.id.rvCategorias);
        rvProductos = findViewById(R.id.rvProductos);
        btnOpciones = findViewById(R.id.btnOpciones);
        btnEnviar = findViewById(R.id.btnEnviar);

        Intent i = getIntent();
        mesaId = i.getLongExtra("mesaId", -1);
        mesaNombre = i.getStringExtra("mesaNombre");
        camareroNombre = i.getStringExtra("camareroNombre");
        if (mesaNombre == null) mesaNombre = "";
        if (camareroNombre == null) camareroNombre = "nombre";
        tvHeader.setText(mesaNombre + " | " + camareroNombre);

        rvCategorias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvProductos.setLayoutManager(new LinearLayoutManager(this));

        categoriaAdapter = new CategoriaAdapter(categorias, new CategoriaAdapter.OnCategoriaClick() {
            @Override
            public void onClick(Categoria c) {
                cargarProductosPorCategoria(c.getId());
            }
        });
        rvCategorias.setAdapter(categoriaAdapter);

        productoAdapter = new ProductoAdapter(productos, new ProductoAdapter.OnProductoClick() {
            @Override
            public void onClick(Producto p) {
                Integer q = seleccion.get(p.getId());
                if (q == null) q = 0;
                seleccion.put(p.getId(), q + 1);
                productosIndexados.put(p.getId(), p);
                Toast.makeText(PedidoActivity.this, p.getNombre() + " x" + (q + 1), Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos.setAdapter(productoAdapter);

        btnBack.setOnClickListener(v -> finish());

        btnOpciones.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(PedidoActivity.this, btnOpciones);
            popup.getMenuInflater().inflate(R.menu.menu_pedido, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_ver_comanda_abierta) {
                        Intent intent = new Intent(PedidoActivity.this, ComandaAbiertaActivity.class);
                        intent.putExtra("mesaId", mesaId);
                        intent.putExtra("mesaCodigo", mesaNombre);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
            popup.show();
        });

        btnEnviar.setOnClickListener(v -> mostrarDialogoConfirmacion());

        cargarCategorias();
        cargarProductosTodos();
    }

    private void mostrarDialogoConfirmacion() {
        if (seleccion.isEmpty()) {
            Toast.makeText(this, "Sin productos", Toast.LENGTH_SHORT).show();
            return;
        }
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this);
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_confirm_comanda, null);
        androidx.recyclerview.widget.RecyclerView rv = view.findViewById(R.id.rvLineasConfirm);
        android.widget.Button btnCancelar = view.findViewById(R.id.btnCancelarConfirm);
        android.widget.Button btnEnviar = view.findViewById(R.id.btnEnviarConfirm);

        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        final ConfirmLineaAdapter[] holder = new ConfirmLineaAdapter[1];

        ConfirmLineaAdapter.OnLineaLongClick listener = new ConfirmLineaAdapter.OnLineaLongClick() {
            @Override
            public void onLongClick(long productoId) {
                editarCantidad(productoId, holder[0]);
            }
        };

        holder[0] = new ConfirmLineaAdapter(seleccion, productosIndexados, listener);
        rv.setAdapter(holder[0]);

        b.setView(view);
        android.app.AlertDialog dlg = b.create();

        btnCancelar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dlg.dismiss();
            }
        });

        btnEnviar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dlg.dismiss();
                enviarComandaDesdeSeleccion();
            }
        });

        dlg.show();
    }

    private void editarCantidad(long productoId, ConfirmLineaAdapter adapter) {
        android.widget.EditText et = new android.widget.EditText(this);
        et.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        Integer actual = seleccion.get(productoId);
        if (actual != null) et.setText(String.valueOf(actual));

        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this);
        b.setTitle("Cantidad");
        b.setView(et);
        b.setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                try {
                    int q = Integer.parseInt(et.getText().toString().trim());
                    if (q < 0) {
                        Toast.makeText(PedidoActivity.this, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (q == 0) {
                        seleccion.remove(productoId);
                    } else {
                        seleccion.put(productoId, q);
                    }
                    adapter.refresh();
                } catch (NumberFormatException ex) {
                    Toast.makeText(PedidoActivity.this, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setNegativeButton("Cancelar", null);
        b.show();
    }

    private void enviarComandaDesdeSeleccion() {
        if (seleccion.isEmpty()) {
            Toast.makeText(this, "Sin productos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        List<ComandaRequest.Linea> lineas = new ArrayList<>();
        for (Map.Entry<Long, Integer> e : seleccion.entrySet()) {
            Producto p = productosIndexados.get(e.getKey());
            if (p == null) continue;
            lineas.add(new ComandaRequest.Linea(
                    p.getId(),
                    e.getValue(),
                    null,
                    p.getPrecioVenta(),
                    p.getIvaTipo()
            ));
        }

        final ComandaRequest req = new ComandaRequest();
        req.setMesaId(mesaId);
        req.setCamareroId(1L);
        req.setLineas(lineas);

        api.crearComanda(req).enqueue(new Callback<ComandaRequest.ComandaRespuesta>() {
            @Override
            public void onResponse(Call<ComandaRequest.ComandaRespuesta> call, Response<ComandaRequest.ComandaRespuesta> resp) {
                if (resp.isSuccessful()) {
                    seleccion.clear();
                    Toast.makeText(PedidoActivity.this, "Comanda enviada", Toast.LENGTH_LONG).show();

                    finish();
                } else {
                    Toast.makeText(PedidoActivity.this, "Error HTTP: " + resp.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ComandaRequest.ComandaRespuesta> call, Throwable t) {

                try {
                    Gson gson = new Gson();
                    String bodyJson = gson.toJson(req);
                    PendingComandaStore.enqueue(
                            getApplicationContext(),
                            mesaId,
                            bodyJson,
                            System.currentTimeMillis()
                    );
                    seleccion.clear();
                    Toast.makeText(
                            PedidoActivity.this,
                            "No hay conexión, la comanda se enviará automáticamente cuando vuelva la red.",
                            Toast.LENGTH_LONG
                    ).show();
                                        finish();
                } catch (Exception ex) {
                    Toast.makeText(
                            PedidoActivity.this,
                            "Fallo al encolar comanda offline: " + ex.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void cargarCategorias() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.obtenerCategorias().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    categorias.clear();
                    Categoria todas = new Categoria();
                    todas.setId(null);
                    todas.setNombre("Todas");
                    categorias.add(todas);
                    categorias.addAll(resp.body());
                    categoriaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
            }
        });
    }

    private void cargarProductosTodos() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.obtenerProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    productos.clear();
                    productosIndexados.clear();
                    for (Producto p : resp.body()) {
                        productos.add(p);
                        productosIndexados.put(p.getId(), p);
                    }
                    productoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
            }
        });
    }

    private void cargarProductosPorCategoria(Long categoriaId) {
        if (categoriaId == null) {
            cargarProductosTodos();
            return;
        }
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.obtenerProductosPorCategoria(categoriaId).enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    productos.clear();
                    for (Producto p : resp.body()) {
                        productos.add(p);
                        productosIndexados.put(p.getId(), p);
                    }
                    productoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
            }
        });
    }
}
