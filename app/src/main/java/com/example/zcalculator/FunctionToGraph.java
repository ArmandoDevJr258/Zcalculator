package com.example.zcalculator;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FunctionToGraph extends AppCompatActivity {

    private ImageButton showkeyPad;
    private EditText functionInput,activeInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_function_to_graph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        showkeyPad = findViewById(R.id.showkeyPad);
        functionInput= findViewById(R.id.functionInput);


        functionInput.setOnClickListener((e->{


            showkeyPad.setVisibility(VISIBLE);
        }));
    }
}