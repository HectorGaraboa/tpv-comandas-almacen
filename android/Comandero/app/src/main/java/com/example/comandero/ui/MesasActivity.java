package com.example.comandero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comandero.R;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import com.example.comandero.model.Mesa;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MesaAdapter adapter;
    private long usuarioId;
    private String usuarioNombre;
    private final Handler handler = new Handler();
    private final Runnable refresco = new Runnable() {
        @Override
        public void run() {
            cargarMesas();
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);
        usuarioId = getIntent().getLongExtra("usuarioId", -1);
        usuarioNombre = getIntent().getStringExtra("usuarioNombre");
        if (usuarioNombre == null) {
            usuarioNombre = "camarero";
        }

        recyclerView = findViewById(R.id.recyclerViewMesas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarMesas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(refresco, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(refresco);
    }

    private void cargarMesas() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<Mesa>> call = apiService.obtenerMesas();
        call.enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(Call<List<Mesa>> call, Response<List<Mesa>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (adapter == null) {
                        adapter = new MesaAdapter(response.body(), new MesaAdapter.OnMesaClickListener() {
                            @Override
                            public void onMesaClick(Mesa m) {
                                Intent i = new Intent(MesasActivity.this, PedidoActivity.class);
                                i.putExtra("mesaId", m.getId());
                                i.putExtra("mesaNombre", m.getCodigo());
                                i.putExtra("camareroId", usuarioId);
                                i.putExtra("camareroNombre", usuarioNombre);
                                startActivity(i);

                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.setDatos(response.body());
                    }
                } else {
                    Toast.makeText(MesasActivity.this, "Error HTTP: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mesa>> call, Throwable t) {
                Toast.makeText(MesasActivity.this, "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
