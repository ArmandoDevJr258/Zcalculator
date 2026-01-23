package com.example.zcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {
    private Spinner languageSpinner;
    private ImageButton btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        languageSpinner = findViewById(R.id.languageSpinner);
        btnReturn = findViewById(R.id.btnReturn);

        String[] Languages ={
                "English",
                "Portuguses"
        };
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,Languages);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        //return to the home screen
        btnReturn.setOnClickListener((e->{

            startActivity(new Intent(Settings.this, MainActivity.class));
        }));

    }
}