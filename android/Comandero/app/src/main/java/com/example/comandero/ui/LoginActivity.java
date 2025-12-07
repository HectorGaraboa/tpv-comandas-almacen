package com.example.comandero.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comandero.R;
import com.example.comandero.api.ApiService;
import com.example.comandero.api.RetrofitClient;
import com.example.comandero.model.dto.LoginRequest;
import com.example.comandero.model.dto.UsuarioSesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etPassword;
    private Button btnEntrar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerLogin();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void hacerLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Usuario y contraseña obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        LoginRequest req = new LoginRequest();
        req.setUsuario(usuario);
        req.setPassword(password);

        api.login(req).enqueue(new Callback<UsuarioSesion>() {
            @Override
            public void onResponse(Call<UsuarioSesion> call, Response<UsuarioSesion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UsuarioSesion sesion = response.body();

                    Intent i = new Intent(LoginActivity.this, MesasActivity.class);
                    i.putExtra("usuarioId", sesion.getId());
                    i.putExtra("usuarioNombre", sesion.getNombre());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioSesion> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
