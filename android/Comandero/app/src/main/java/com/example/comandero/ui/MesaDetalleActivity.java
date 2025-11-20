package com.example.comandero.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comandero.R;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import com.example.comandero.model.MesaAbierta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesaDetalleActivity extends AppCompatActivity {

    private TextView txtMesaCodigo;
    private TextView txtTotal;

    private long mesaId;
    private String mesaCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa_detalle);

        txtMesaCodigo = findViewById(R.id.txtMesaCodigo);
        txtTotal = findViewById(R.id.txtTotal);

        mesaId = getIntent().getLongExtra("mesaId", -1);
        mesaCodigo = getIntent().getStringExtra("mesaCodigo");

        if (mesaId == -1 || mesaCodigo == null) {
            Toast.makeText(this, "Datos de mesa inválidos", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        txtMesaCodigo.setText("Mesa: " + mesaCodigo);
        cargarMesaAbierta();
    }

    private void cargarMesaAbierta() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<MesaAbierta> call = apiService.obtenerMesaAbierta(mesaId);
        call.enqueue(new Callback<MesaAbierta>() {
            @Override
            public void onResponse(Call<MesaAbierta> call, Response<MesaAbierta> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MesaAbierta dto = response.body();
                    txtTotal.setText(String.format("Total: %.2f €", dto.getTotal()));
                } else {
                    Toast.makeText(MesaDetalleActivity.this, "Error HTTP: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MesaAbierta> call, Throwable t) {
                Toast.makeText(MesaDetalleActivity.this, "Fallo: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
