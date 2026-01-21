package com.example.zcalculator;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.button.MaterialButton;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainCalculator extends AppCompatActivity {
    private LinearLayout header;
    private ConstraintLayout  basicCalculatorLayout,cientificCalculatorLayout;
    private ImageButton btnrReturn;
    private TextView txtbasic,txtcientific,txtResult;
    private EditText operationinput,resultinput,activeInput;
    private Button btnc,btnparenteses,btnpercent,btnequals,btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0,btndot,btnplus,btnminus,btntimes,btndevide;

    private double firstOperand =0;
    private double secondOperand =0;
     private String currentOperator ="";
    private  boolean isOperatorClicked  = false;
    private boolean justPressedOperator = false;
    private Boolean isUpdating = false;
    private MaterialButton btnClear,btnConverters;

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
        btnplus= findViewById(R.id.btnplus);
        btnminus= findViewById(R.id.btnminus);
        btntimes= findViewById(R.id.btntimes);
        btndevide= findViewById(R.id.btndevide);
        operationinput = findViewById(R.id.operationinput);
        resultinput = findViewById(R.id.resultinput);
        txtResult = findViewById(R.id.txtResult);
        btnClear = findViewById(R.id.btnClear);
        btnConverters = findViewById(R.id.btnConverters);



        btn9.setOnClickListener((e->{
            appendToActiveInput("9");
        }));

        btn8.setOnClickListener((e->{
            appendToActiveInput("8");
        }));

        btn7.setOnClickListener((e->{
            appendToActiveInput("7");

        }));

        btn6.setOnClickListener((e->{
            appendToActiveInput("6");
        }));

        btn5.setOnClickListener((e->{
            appendToActiveInput("5");
        }));

        btn4.setOnClickListener((e->{
            appendToActiveInput("4");
        }));

        btn3.setOnClickListener((e->{
            appendToActiveInput("3");
        }));

        btn2.setOnClickListener((e->{
            appendToActiveInput("2");
        }));

        btn1.setOnClickListener((e->{
            appendToActiveInput("1");
        }));

        btn0.setOnClickListener((e->{
            appendToActiveInput("0");
        }));
        btnparenteses.setOnClickListener(e -> {
            handleParentheses();
        });


        btnpercent.setOnClickListener(e -> {
            appendToActiveInput("%");
        });

        btndot.setOnClickListener((e->{
            appendToActiveInput(".");
        }));

        btnequals.setOnClickListener(e -> {
            calculateResult();
            txtResult.setVisibility(VISIBLE);
            resultinput.setAlpha(1.0f);
        });



        btnc.setOnClickListener(e -> {
            operationinput.setText("");
            resultinput.setText("");
            txtResult.setVisibility(GONE);
            resultinput.setAlpha(0.2f);

        });
        btnClear.setOnClickListener((e->{
            if (activeInput==null) return;
            String current = activeInput.getText().toString();
            if (!current.isEmpty()){
                activeInput.setText(current.substring(0,current.length()-1));


                activeInput.setSelection(activeInput.getText().length()); }
        }));
        btnConverters.setOnClickListener((e->{
            Intent intent = new Intent(MainCalculator.this, Quickies.class);
            startActivity(intent);
        }));


        btnplus.setOnClickListener((e->{
            appendToActiveInput("+");
            btnplus.setTextColor(Color.parseColor("#ffd402"));

        }));
        btnminus.setOnClickListener((e->{
            appendToActiveInput("-");
            btnminus.setTextColor(Color.parseColor("#ffd402"));
        }));
        btntimes.setOnClickListener((e->{
            appendToActiveInput("*");
            btntimes.setTextColor(Color.parseColor("#ffd402"));
        }));
        btndevide.setOnClickListener((e->{

            appendToActiveInput("/");
            btndevide.setTextColor(Color.parseColor("#ffd402"));
        }));

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
        operationinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (isUpdating) return;
                if (charSequence.length() == 0) {
                    isUpdating = true;
                    operationinput.setText("");
                    isUpdating = false;
                    return;
                }


                isUpdating = true;
//                double value = Double.parseDouble(charSequence.toString());
//                String fromUnit = spinner1.getSelectedItem().toString();
//                String toUnit = spinner2.getSelectedItem().toString();
//
//                double result = convertTemperature(value,fromUnit,toUnit);
//                input2.setText(String.valueOf(result));
//                isUpdating =false;
            }
        });
        activeInput= operationinput;
        operationinput.setOnClickListener((e->{
            activeInput =operationinput;
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
    private void appendToActiveInput(String text) {
        if (activeInput == null) return;

        String current = activeInput.getText().toString();

        // Prevent starting with operators except '('
        if (current.isEmpty() && "+*/)%".contains(text)) return;

        // Prevent double operators
        if (!current.isEmpty()) {
            char last = current.charAt(current.length() - 1);

            if ("+-*/".contains(text) && "+-*/".indexOf(last) != -1) return;
            if (text.equals(")") && "+-*/(".indexOf(last) != -1) return;
            if (text.equals("%") && last == '%') return;
        }

        activeInput.setText(current + text);
        activeInput.setSelection(activeInput.getText().length());
    }

    private void calculateResult() {
        String expressionText = operationinput.getText().toString();
        if (expressionText.isEmpty()) return;

        try {
            expressionText = autoCloseParentheses(expressionText);
            expressionText = handlePercentage(expressionText);

            Expression expression = new ExpressionBuilder(expressionText).build();
            double result = expression.evaluate();

            if (result == (long) result) {
                resultinput.setText(String.valueOf((long) result));
            } else {
                resultinput.setText(String.valueOf(result));
            }

        } catch (Exception e) {
            resultinput.setText("Error");
        }
    }
    private String autoCloseParentheses(String expr) {
        int open = 0, close = 0;

        for (char c : expr.toCharArray()) {
            if (c == '(') open++;
            if (c == ')') close++;
        }

        while (open > close) {
            expr += ")";
            close++;
        }

        return expr;
    }

    private String handlePercentage(String expr) {

        // 50% -> (50/100)
        expr = expr.replaceAll("(\\d+(?:\\.\\d+)?)%", "($1/100)");

        // Handle: A + B%  OR  A - B%
        expr = expr.replaceAll(
                "(\\d+(?:\\.\\d+)?)([+-])\\((\\d+(?:\\.\\d+)?)/100\\)",
                "($1$2($1*$3/100))"
        );

        // Handle: A * B%  OR  A / B%
        expr = expr.replaceAll(
                "(\\d+(?:\\.\\d+)?)([*/])\\((\\d+(?:\\.\\d+)?)/100\\)",
                "($1$2($3/100))"
        );

        return expr;
    }
    private void handleParentheses() {
        String text = operationinput.getText().toString();

        int openCount = 0;
        int closeCount = 0;

        for (char c : text.toCharArray()) {
            if (c == '(') openCount++;
            if (c == ')') closeCount++;
        }

        // If empty or last char is operator or '(' → add '('
        if (text.isEmpty() ||
                "+-*/(".contains(String.valueOf(text.charAt(text.length() - 1)))) {
            appendToActiveInput("(");
            return;
        }

        // If there are unclosed '(' → add ')'
        if (openCount > closeCount) {
            appendToActiveInput(")");
            return;
        }

        // Otherwise add '('
        appendToActiveInput("(");
    }



}