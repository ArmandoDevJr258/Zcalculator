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

    private double firstOperand =0;
    private double secondOperand =0;
     private String currentOperator ="";
    private  boolean isOperatorClicked  = false;
    private boolean justPressedOperator = false;

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



        btn9.setOnClickListener((e->{
            addNumber("9");
        }));

        btn8.setOnClickListener((e->{
            addNumber(btn8.getText().toString());
        }));

        btn7.setOnClickListener((e->{
            addNumber(btn7.getText().toString());
        }));

        btn6.setOnClickListener((e->{
            addNumber(btn6.getText().toString());
        }));

        btn5.setOnClickListener((e->{
            addNumber(btn5.getText().toString());
        }));

        btn4.setOnClickListener((e->{
            addNumber(btn4.getText().toString());
        }));

        btn3.setOnClickListener((e->{
            addNumber(btn3.getText().toString());
        }));

        btn2.setOnClickListener((e->{
            addNumber(btn2.getText().toString());
        }));

        btn1.setOnClickListener((e->{
            addNumber(btn1.getText().toString());
        }));

        btn0.setOnClickListener((e->{
            addNumber(btn0.getText().toString());
        }));

        btndot.setOnClickListener((e->{
            addNumber(btndot.getText().toString());
        }));

        btnequals.setOnClickListener((e->{
          calculteResult();
        }));


        btnc.setOnClickListener((e->{
            clearAll();
        }));


        btnplus.setOnClickListener((e->{
           clickOperator("+");
        }));
        btnminus.setOnClickListener((e->{
            clickOperator("-");
        }));
        btntimes.setOnClickListener((e->{
            clickOperator("*");
        }));
        btndevide.setOnClickListener((e->{
            clickOperator("/");
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
    private void addNumber(String number) {
        // 1. Update the top display (the full expression)
        operationinput.append(number);

        // 2. Update the bottom display (the current number being typed)
        if (isOperatorClicked) {
            // If an operator was just pressed, start a new number
            resultinput.setText(number);
            isOperatorClicked = false;
        } else {
            // Otherwise, keep appending digits (e.g., 8 then 3 makes 83)
            resultinput.append(number);
        }
    }

    private void clickOperator(String operator) {
        String currentVal = resultinput.getText().toString();

        // Prevent clicking operator if no number exists
        if (currentVal.isEmpty() || currentVal.equals(".")) return;

        // Store the first number
        firstOperand = Double.parseDouble(currentVal);
        currentOperator = operator;

        // Tell addNumber that the next digit belongs to the second operand
        isOperatorClicked = true;

        // Update top display: "8" becomes "8 + "
        operationinput.append(" " + operator + " ");
    }

    private void calculteResult() {
        String currentVal = resultinput.getText().toString();

        // Validation: need an operator and a second number
        if (currentOperator.isEmpty() || currentVal.isEmpty()) return;

        secondOperand = Double.parseDouble(currentVal);
        double result = 0;

        switch (currentOperator) {
            case "+": result = firstOperand + secondOperand; break;
            case "-": result = firstOperand - secondOperand; break;
            case "*": result = firstOperand * secondOperand; break;
            case "/":
                if (secondOperand == 0) {
                    resultinput.setText("Error");
                    return;
                }
                result = firstOperand / secondOperand;
                break;
        }

        // Display the final result in the bottom
        resultinput.setText(String.valueOf(result));

        // Reset the top display to show just the result for the next operation
        operationinput.setText(String.valueOf(result));

        // Prepare for continuous calculation (e.g., result + 5)
        firstOperand = result;
        currentOperator = "";
        isOperatorClicked = true;
    }


    private  void  clearAll(){
        operationinput.setText("");
        resultinput.setText("");
        firstOperand= 0;
        secondOperand = 0;
        currentOperator = "";
        isOperatorClicked = false;
    }

}