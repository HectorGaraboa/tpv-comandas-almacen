package com.example.comandero.ui;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.comandero.R;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import com.example.comandero.model.MesaAbierta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComandaAbiertaActivity extends AppCompatActivity {

    private long mesaId;
    private String mesaCodigo;
    private RecyclerView recycler;
    private ComandaAbiertaAdapter adapter;
    private TextView txtTitulo;
    private TextView txtTotal;
    private Button btnAtras;
    private Button btnImpresion;

    private final Handler handler = new Handler();
    private final Runnable refresco = new Runnable() { @Override public void run() { cargar(); handler.postDelayed(this, 5000); } };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_abierta);

        mesaId = getIntent().getLongExtra("mesaId", -1);
        mesaCodigo = getIntent().getStringExtra("mesaCodigo");

        recycler = findViewById(R.id.recyclerComandaAbierta);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComandaAbiertaAdapter();
        recycler.setAdapter(adapter);

        txtTitulo = findViewById(R.id.txtTituloMesa);
        txtTotal = findViewById(R.id.txtTotalAbierto);
        btnAtras = findViewById(R.id.btnAtras);
        btnImpresion = findViewById(R.id.btnImpresion);

        if (mesaCodigo != null) txtTitulo.setText(mesaCodigo); else txtTitulo.setText("");

        btnAtras.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { finish(); }});
        btnImpresion.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { Toast.makeText(ComandaAbiertaActivity.this, "Sin funcionalidad", Toast.LENGTH_SHORT).show(); }});

        cargar();
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

    private void cargar() {
        if (mesaId <= 0) {
            Toast.makeText(this, "Mesa inválida", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.obtenerMesaAbierta(mesaId).enqueue(new Callback<MesaAbierta>() {
            @Override
            public void onResponse(Call<MesaAbierta> call, Response<MesaAbierta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MesaAbierta dto = response.body();
                    adapter.setItems(dto.getItems());
                    txtTotal.setText(String.format("Total: %.2f €", dto.getTotal()));
                } else {
                    Toast.makeText(ComandaAbiertaActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<MesaAbierta> call, Throwable t) {
                Toast.makeText(ComandaAbiertaActivity.this, "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
