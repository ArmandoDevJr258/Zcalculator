package com.example.zcalculator.quickies;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zcalculator.Quickies;
import com.example.zcalculator.R;
import com.google.android.material.button.MaterialButton;

public class Time extends AppCompatActivity {

    private Spinner spinner1,spinner2;
    private ImageButton btnReturn;
    private TextView unitTxt1,unitTxt2;
    private EditText input1,input2,activeInput;
    private Boolean isUpdating = false;
    private Button btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0;
    private MaterialButton btnclear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinner1 =findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btnReturn = findViewById(R.id.btnReturn);
        unitTxt1 = findViewById(R.id.unitTxt1);
        unitTxt2 = findViewById(R.id.unitTxt2);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
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
        btnclear = findViewById(R.id.btnclear);

        String[] timeUnits1 ={
                "Milliseconds",
                "Seconds",
                "Minutes",
                "Hours",
                "Days",
                "Weeks",
                "Months",
                "Years"
        };

        String[] timeUnits2 ={
                "Milliseconds",
                "Seconds",
                "Minutes",
                "Hours",
                "Days",
                "Weeks",
                "Months",
                "Years"
        };
        //Triggering action to each button number pad
        btn0.setOnClickListener((e->{
            appendToActiveInput("0");
        }));
        btn1.setOnClickListener((e->{
            appendToActiveInput("1");
        }));

        btn2.setOnClickListener((e->{
            appendToActiveInput("2");
        }));
        btn3.setOnClickListener((e->{
            appendToActiveInput("3");
        }));
        btn4.setOnClickListener((e->{
            appendToActiveInput("4");
        }));
        btn5.setOnClickListener((e->{
            appendToActiveInput("5");
        }));
        btn6.setOnClickListener((e->{
            appendToActiveInput("6");
        }));
        btn7.setOnClickListener((e->{
            appendToActiveInput("7");
        }));
        btn8.setOnClickListener((e->{
            appendToActiveInput("8");
        }));
        btn9.setOnClickListener((e->{
            appendToActiveInput("9");
        }));
        btnclear.setOnClickListener((e->{
            if (activeInput==null) return;
            String current = activeInput.getText().toString();
            if (!current.isEmpty()){
                activeInput.setText(current.substring(0,current.length()-1));


                activeInput.setSelection(activeInput.getText().length()); }
        }));


        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,timeUnits1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 =new ArrayAdapter<>(this, R.layout.spinner_item,timeUnits2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //return to the quickies screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Time.this, Quickies.class);
            startActivity(intent);
        }));

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (isUpdating) return;
                if(charSequence.length()==0){
                    input2.setText("");
                    return;
                }

                isUpdating = true;
                double value = Double.parseDouble(charSequence.toString());
                String fromUnit = spinner1.getSelectedItem().toString();
                String toUnit = spinner2.getSelectedItem().toString();

                double result = convertTime(value,fromUnit,toUnit);
                input2.setText(String.valueOf(result));
                isUpdating =false;
            }
        });

        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (isUpdating) return;
                if (charSequence.length()==0){
                    input1.setText("");
                    return;
                }
                isUpdating =true;

                double value = Double.parseDouble(charSequence.toString());
                String fromUnit = spinner2.getSelectedItem().toString();
                String toUnit = spinner1.getSelectedItem().toString();

                double result= convertTime(value,fromUnit,toUnit);
                input1.setText(String.valueOf(result));

                isUpdating= false;
            }
        });
        input1.setOnClickListener((e->{
            activeInput =input1;
        }));
        input2.setOnClickListener((e->{
            activeInput =input2;
        }));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getItemAtPosition(i).toString();
                if (itemSelected.equals("Milliseconds")){
                    unitTxt1.setText("ms");
                } else if (itemSelected.equals("Seconds")) {
                    unitTxt1.setText("s");
                }
                else if (itemSelected.equals("Minutes")) {
                    unitTxt1.setText("min");
                }
                else if (itemSelected.equals("Hours")) {
                    unitTxt1.setText("h");
                }
                else if (itemSelected.equals("Days")) {
                    unitTxt1.setText("d");
                }
                else if (itemSelected.equals("Weeks")) {
                    unitTxt1.setText("wk");
                }
                else if (itemSelected.equals("Months")) {
                    unitTxt1.setText("mth");
                }
                else if (itemSelected.equals("Years")) {
                    unitTxt1.setText("Y");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected = adapterView.getItemAtPosition(i).toString();
                if (itemSelected.equals("Milliseconds")){
                    unitTxt2.setText("ms");
                } else if (itemSelected.equals("Seconds")) {
                    unitTxt2.setText("s");
                }
                else if (itemSelected.equals("Minutes")) {
                    unitTxt2.setText("min");
                }
                else if (itemSelected.equals("Hours")) {
                    unitTxt2.setText("h");
                }
                else if (itemSelected.equals("Days")) {
                    unitTxt2.setText("d");
                }
                else if (itemSelected.equals("Weeks")) {
                    unitTxt2.setText("wk");
                }
                else if (itemSelected.equals("Months")) {
                    unitTxt2.setText("mth");
                }
                else if (itemSelected.equals("Years")) {
                    unitTxt2.setText("Y");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private  double convertTime(double value,String from,String to){


        if (from.equals(to)){
            return value;
        }
        if(from.equals("Milliseconds")&&to.equals("Seconds")){
            return value*0.001;
        }
        if(from.equals("Milliseconds")&&to.equals("Minutes")){
            return value*0.0000166667;
        }
        if(from.equals("Milliseconds")&&to.equals("Hours")){
            return value*0.001;
        }
        if(from.equals("Milliseconds")&&to.equals("Days")){
            return value-273.15;
        }
        if(from.equals("Milliseconds")&&to.equals("Weeks")){
            return (value-32)*5/9+273.15;
        }
        if(from.equals("Milliseconds")&&to.equals("Months")){
            return (value-273.15)*9/5+32;
        }
        if(from.equals("Milliseconds")&&to.equals("Years")){
            return (value-273.15)*9/5+32;
        }

        //from seconds
        if(from.equals("Seconds")&&to.equals("Milliseconds")){
            return value*1000;
        }
        if(from.equals("Seconds")&&to.equals("Minutes")){
            return value*0.0166666667;
        }
        if(from.equals("Seconds")&&to.equals("Hours")){
            return value*0.0002777778;
        }
        if(from.equals("Seconds")&&to.equals("Days")){
            return value*0.0000115741;
        }
        if(from.equals("Seconds")&&to.equals("Weeks")){
            return value*0.0000016534;
        }

        //from minutes
        if(from.equals("Minutes")&&to.equals("Milliseconds")){
            return value*60000;
        }
        if(from.equals("Minutes")&&to.equals("Seconds")){
            return value*60;
        }
        if(from.equals("Minutes")&&to.equals("Hours")){
            return value*0.0166666667;
        }
        if(from.equals("Minutes")&&to.equals("Days")){
            return value*0.0006944444;
        }
        if(from.equals("Minutes")&&to.equals("Weeks")){
            return value*0.0000992063;
        }

        //from Hours


        return  value;

    }
    private void appendToActiveInput(String text){
        if (activeInput ==null) return;

        String current = activeInput.getText().toString();
        if (text.equals(".")&& current.contains(".")) return;
        activeInput.setText(current+text);
        activeInput.setSelection(activeInput.getText().length());

    }
}