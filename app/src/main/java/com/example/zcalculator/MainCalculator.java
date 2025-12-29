package com.example.zcalculator;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText operationinput,resultinput;
    private Button btnc,btnparenteses,btnpercent,btnequals,btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0,btndot,btnplus,btnminus,btntimes,btndevide;

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
        btnc = findViewById(R.id.btnc);
        btnparenteses= findViewById(R.id.btnparenteses);
        btnpercent= findViewById(R.id.btnpercent);
        btnequals= findViewById(R.id.btnequals);
        btn9= findViewById(R.id.btn9);
        btn8= findViewById(R.id.btn8);
        btn7= findViewById(R.id.btn7);
        btn6= findViewById(R.id.btn6);
        btn5= findViewById(R.id.btn5);
        btn4= findViewById(R.id.btn4);
        btn3= findViewById(R.id.btn3);
        btn2= findViewById(R.id.btn2);
        btn1= findViewById(R.id.btn1);
        btn0= findViewById(R.id.btn0);
        btndot= findViewById(R.id.btndot);





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