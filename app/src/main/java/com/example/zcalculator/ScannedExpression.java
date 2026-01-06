package com.example.zcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ScannedExpression extends AppCompatActivity {

    private EditText scannedExpression;
    private TextView txtResult;
    private ImageButton btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scanned_expression);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scannedExpression = findViewById(R.id.expresstionScanned);
        txtResult = findViewById(R.id.txtResult);
        btnReturn = findViewById(R.id.btnReturn);


        //return to the scan screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(ScannedExpression.this,ScanAndSolve.class);
            startActivity(intent);
        }));
        String scannedText = getIntent().getStringExtra("scanned_text");
        if (scannedText != null) {
            scannedExpression.setText(scannedText);


            String result = evaluateMathExpression(scannedText);
            txtResult.setText(result);
        }

    }
    private String evaluateMathExpression(String expressionText) {
        try {

            expressionText = expressionText.replaceAll("\\s+", "");


            Expression expression = new ExpressionBuilder(expressionText).build();
            double result = expression.evaluate();


            if (result == (long) result) {
                return String.valueOf((long) result);
            } else {
                return String.valueOf(result);
            }

        } catch (Exception e) {

            return "Error: invalid math expression";
        }
    }
}