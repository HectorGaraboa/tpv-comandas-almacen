package com.example.comandero.ui;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comandero.R;
import com.example.comandero.api.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private Button btnComandas, btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnComandas = findViewById(R.id.btnComandas);
        btnActualizar = findViewById(R.id.btnActualizar);

        btnComandas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoServidor();
            }
        });
    }

    private void mostrarDialogoServidor() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        String current = RetrofitClient.getBaseUrl();
        if (current.endsWith("/")) {
            current = current.substring(0, current.length() - 1);
        }
        input.setText(current);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Configurar servidor");
        builder.setMessage("Introduce la URL base del servidor");
        builder.setView(input);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = input.getText().toString().trim();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                RetrofitClient.setBaseUrl(url);
                SharedPreferences prefs = getSharedPreferences("tpv_prefs", MODE_PRIVATE);
                prefs.edit().putString("api_base_url", RetrofitClient.getBaseUrl()).apply();
                Toast.makeText(MainActivity.this, "Servidor actualizado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
