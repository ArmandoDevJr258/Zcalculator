package com.example.zcalculator;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainCalculator extends AppCompatActivity {
    private LinearLayout header;
    private ConstraintLayout  basicCalculatorLayout,cientificCalculatorLayout;
    private ImageButton btnrReturn;
    private TextView txtbasic,txtcientific;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        header = findViewById(R.id.header);
        basicCalculatorLayout=findViewById(R.id.basicCalculatorLayout);
        cientificCalculatorLayout=findViewById(R.id.cientificCalculatorLayout);
        btnrReturn=findViewById(R.id.btnReturn);
        txtbasic = findViewById(R.id.txtbasic);
        txtcientific = findViewById(R.id.txtcientific);


        //return to home screen
        btnrReturn.setOnClickListener((e->{
            Intent intent = new Intent(MainCalculator.this,MainActivity.class);
            startActivity(intent);

        }));
        txtbasic.setOnClickListener((e->{
            cientificCalculatorLayout.setVisibility(INVISIBLE);
            basicCalculatorLayout.setVisibility(VISIBLE);
            changeCalculator(txtbasic);
        }));
        txtcientific.setOnClickListener((e->{
            basicCalculatorLayout.setVisibility(INVISIBLE);
            cientificCalculatorLayout.setVisibility(VISIBLE);
            changeCalculator(txtcientific);
        }));

    }
    private void changeCalculator(TextView selected){
        TextView[] textViews ={txtbasic,txtcientific};
        for(TextView txt :textViews){
            if(txt==selected){
                txt.setAlpha(1);

            }else{
                txt.setAlpha(0.3f);
            }

        }
    }
}