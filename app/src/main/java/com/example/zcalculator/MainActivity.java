package com.example.zcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private LinearLayout quickiesCard,mainCalculatorCard,graphCard,scantoSolveCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        quickiesCard= findViewById(R.id.quickiesCard);
        mainCalculatorCard=findViewById(R.id.mainCalculatorCard);
        graphCard= findViewById(R.id.graphCard);
        scantoSolveCard= findViewById(R.id.scantoSolveCard);

        //
        quickiesCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, Quickies.class);
            startActivity(intent);

        }));

        //open main calculator screen
        mainCalculatorCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, MainCalculator.class);
            startActivity(intent);
        }));
        //open the Graphs screen
        graphCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, Graphs.class);
            startActivity(intent);
        }));

        //open the Scan to solve  screen
        scantoSolveCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, ScanAndSolve.class);
            startActivity(intent);
        }));

    }
}