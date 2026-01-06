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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zcalculator.Quickies;
import com.example.zcalculator.R;
import com.google.android.material.button.MaterialButton;

public class Tempeture extends AppCompatActivity {
    private Spinner spinner1,spinner2;
    private ImageButton btnReturn;
    private TextView unitTxt1,unitTxt2;
    private EditText input1,input2,activeInput;
    private Boolean isUpdating = false;
    private Button btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0,btnc;
    private MaterialButton btnclear,btnup,btndown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tempeture);
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
        btnc= findViewById(R.id.btnc);
        btnup = findViewById(R.id.btnup);
        btndown = findViewById(R.id.btndown);







        String[] temperatureUnits1 ={
                "Celsius",
                "Fahrenheit",
                "Kelvin"

        };
        String[] temperatureUnits2 ={
                "Celsius",
                "Fahrenheit",
                "Kelvin"

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
        btnc.setOnClickListener((e->{
            if (activeInput==null) return;
            String current = activeInput.getText().toString();
            if (!current.isEmpty()){


                 }
        }));

        btnup.setOnClickListener((e->{
            activeInput =input1;
        }));
        btndown.setOnClickListener((e->{
            activeInput =input2;
        }));
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,temperatureUnits1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        ArrayAdapter<String> adapter2 =new ArrayAdapter<>(this, R.layout.spinner_item,temperatureUnits2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //return to the Quickies scren
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Tempeture.this,Quickies.class);
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
                if (charSequence.length() == 0) {
                    isUpdating = true;
                    input2.setText("");
                    isUpdating = false;
                    return;
                }


                isUpdating = true;
                double value = Double.parseDouble(charSequence.toString());
                String fromUnit = spinner1.getSelectedItem().toString();
                String toUnit = spinner2.getSelectedItem().toString();

                double result = convertTemperature(value,fromUnit,toUnit);
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
                if (charSequence.length() == 0) {
                    isUpdating = true;
                    input1.setText("");
                    isUpdating = false;
                    return;
                }

                isUpdating =true;

                double value = Double.parseDouble(charSequence.toString());
                String fromUnit = spinner2.getSelectedItem().toString();
                String toUnit = spinner1.getSelectedItem().toString();

                double result= convertTemperature(value,fromUnit,toUnit);
                input1.setText(String.valueOf(result));

                isUpdating= false;
            }
        });

        activeInput =input1;
        input1.setOnClickListener((e->{
            activeInput =input1;
        }));
        input2.setOnClickListener((e->{
            activeInput =input2;
        }));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem =adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Celsius")){
                    unitTxt1.setText("ºC");
                    input1.setText("");
                    input2.setText("");




                } else if (selectedItem.equals("Kelvin")) {
                    unitTxt1.setText("K");
                    input1.setText("");
                    input2.setText("");

                }else if (selectedItem.equals("Fahrenheit")) {
                    unitTxt1.setText("ºF");
                    input1.setText("");
                    input2.setText("");

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Celsius")){
                    unitTxt2.setText("ºC");
                    input1.setText("");
                    input2.setText("");

                } else if (selectedItem.equals("Kelvin")) {
                    unitTxt2.setText("K");
                    input1.setText("");
                    input2.setText("");

                }
                else if (selectedItem.equals("Fahrenheit")) {
                    unitTxt2.setText("ºF");
                    input1.setText("");
                    input2.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private  double convertTemperature(double value,String from,String to){


        if (from.equals(to)){
            return value;
        }
        if(from.equals("Celsius")&&to.equals("Fahrenheit")){
            return (value *9/5)+32;
        }
        if(from.equals("Fahrenheit")&&to.equals("Celsius")){
            return (value -32)*5/9;
        }
        if(from.equals("Celsius")&&to.equals("Kelvin")){
            return value+273.15;
        }
        if(from.equals("Kelvin")&&to.equals("Celsius")){
            return value-273.15;
        }
        if(from.equals("Fahrenheit")&&to.equals("Kelvin")){
            return (value-32)*5/9+273.15;
        }
        if(from.equals("Kelvin")&&to.equals("Fahrenheit")){
            return (value-273.15)*9/5+32;
        }

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