package com.example.comandero.ui;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comandero.R;

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
                Intent intent = new Intent(MainActivity.this, MesasActivity.class);
                startActivity(intent);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí se forzará la actualización de datos del servidor (mas adelante)
                }
        });
    }
}
