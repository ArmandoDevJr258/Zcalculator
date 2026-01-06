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

public class Area extends AppCompatActivity {
    private Spinner spinner1,spinner2;
    private ImageButton btnReturn;
    private TextView unitTxt1,unitTxt2;
    private EditText input1,input2,activeInput;
    private Boolean isUpdating = false;
    private Button btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0;
    private MaterialButton btnclear,btnup,btndown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_area);
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
        btnup = findViewById(R.id.btnup);
        btndown = findViewById(R.id.btndown);


        String[] areaUnits1 ={
                "Acres",
                "Ares",
                "Hectares",
                "Square centimeters",
                "Square feet",
                "Square inches",
                "Square meters"
        };

        String[] areaUnits2 ={
                "Acres",
                "Ares",
                "Hectares",
                "Square centimeters",
                "Square feet",
                "Square inches",
                "Square meters"
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

        btnup.setOnClickListener((e->{
            activeInput =input1;
        }));
        btndown.setOnClickListener((e->{
            activeInput =input2;
        }));

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,areaUnits1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 =new ArrayAdapter<>(this, R.layout.spinner_item,areaUnits2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //return to Quickies Screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Area.this, Quickies.class);
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

                double result = convertArea(value,fromUnit,toUnit);
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

                double result= convertArea(value,fromUnit,toUnit);
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
                String itemSelected =adapterView.getItemAtPosition(i).toString();
                if (itemSelected.equals("Acres")){

                } else if (itemSelected.equals("Ares")) {
                    unitTxt1.setText("a");


                }else if (itemSelected.equals("Hectares")) {
                    unitTxt1.setText("ha");


                }else if (itemSelected.equals("Square centimeters")) {
                    unitTxt1.setText("cm²");

                }else if (itemSelected.equals("Square feet")) {
                    unitTxt1.setText("ft²");

                }else if (itemSelected.equals("Square inches")) {
                    unitTxt1.setText("in²");

                }else if (itemSelected.equals("Square meters")) {
                    unitTxt1.setText("m²");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =adapterView.getItemAtPosition(i).toString();
                if (itemSelected.equals("Acres")){

                } else if (itemSelected.equals("Ares")) {
                    unitTxt2.setText("a");


                }else if (itemSelected.equals("Hectares")) {
                    unitTxt2.setText("ha");


                }else if (itemSelected.equals("Square centimeters")) {
                    unitTxt2.setText("cm²");

                }else if (itemSelected.equals("Square feet")) {
                    unitTxt2.setText("ft²");

                }else if (itemSelected.equals("Square inches")) {
                    unitTxt2.setText("in²");

                }else if (itemSelected.equals("Square meters")) {
                    unitTxt2.setText("m²");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private  double convertArea(double value,String from,String to){


        if (from.equals(to)){
            return value;
        }
        //from Acres
        if(from.equals("Acres")&&to.equals("Ares")){
            return value*40.468564224;
        }
        if(from.equals("Acres")&&to.equals("Hectares")){
            return value*0.4046856422;
        }
        if(from.equals("Acres")&&to.equals("Square centimeters")){
            return value*40468564.224;
        }
        if(from.equals("Acres")&&to.equals("Square feet")){
            return value*43560;
        }
        if(from.equals("Acres")&&to.equals("Square inches")){
            return value*6272640;
        }
        if(from.equals("Acres")&&to.equals("Square meters")){
            return value*4046.8564224;
        }
        //from Ares
        if(from.equals("Ares")&&to.equals("Acres")){
            return value*40.468564224;
        }
        if(from.equals("Ares")&&to.equals("Hectares")){
            return value*0.01;
        }
        if(from.equals("Ares")&&to.equals("Square centimeters")){
            return value*1000000;
        }
        if(from.equals("Ares")&&to.equals("Square feet")){
            return value*1076.391041671;
        }
        if(from.equals("Ares")&&to.equals("Square inches")){
            return value*155000.31000062;
        }
        if(from.equals("Ares")&&to.equals("Square meters")){
            return value*100;
        }

        //from Hectares
        if(from.equals("Hectares")&&to.equals("Acres")){
            return value*2.4710538147;
        }
        if(from.equals("Hectares")&&to.equals("Ares")){
            return value*100;
        }
        if(from.equals("Hectares")&&to.equals("Square centimeters")){
            return value*100000000;
        }
        if(from.equals("Hectares")&&to.equals("Feet")){
            return value*107639.104167;
        }
        if(from.equals("Hectares")&&to.equals("Square inches")){
            return value*15500031.000062;
        }
        if(from.equals("Hectares")&&to.equals("Square meters")){
            return value*10000;
        }

        //from Square Centimeters
        if(from.equals("Square centimeters")&&to.equals("Acres")){
            return value*2.47105381E-8;
        }
        if(from.equals("Square centimeters")&&to.equals("Ares")){
            return value*0.000001;
        }
        if(from.equals("Square centimeters")&&to.equals("Hectares")){
            return value*1.00000000E-8;
        }
        if(from.equals("Square centimeters")&&to.equals("Square feet")){
            return value*0.001076391;
        }
        if(from.equals("Square centimeters")&&to.equals("Square inches")){
            return value*0.15500031;
        }
        if(from.equals("Square centimeters")&&to.equals("Square meters")){
            return value*0.0001;
        }

        //from Square Feet
        if(from.equals("Square feet")&&to.equals("Acres")){
            return value*0.0000229568;
        }
        if(from.equals("Square feet")&&to.equals("Ares")){
            return value*0.00009290304;
        }
        if(from.equals("Square feet")&&to.equals("Hectares")){
            return value*0.00000082903;
        }
        if(from.equals("Square feet")&&to.equals("Square centimeters")){
            return value*929.0304;
        }
        if(from.equals("Square feet")&&to.equals("Square inches")){
            return value*144;
        }
        if(from.equals("Square feet")&&to.equals("Square meters")){
            return value*0.09290304;
        }

        //from Square Inches
        if(from.equals("Square inches")&&to.equals("Acres")){
            return value*1.59422508E-7;
        }
        if(from.equals("Square inches")&&to.equals("Ares")){
            return value*0.0000064516;
        }
        if(from.equals("Square inches")&&to.equals("Hectares")){
            return value*6.45160000E-8;
        }
        if(from.equals("Square inches")&&to.equals("Square centimeters")){
            return value*6.4516;
        }
        if(from.equals("Square inches")&&to.equals("Square feet")){
            return value*0.0069444444;
        }
        if(from.equals("Square inches")&&to.equals("Square meters")){
            return value*0.00064516;
        }

        //from Square Meters
        if(from.equals("Square meters")&&to.equals("Acres")){
            return value*0.000247105;
        }
        if(from.equals("Square meters")&&to.equals("Ares")){
            return value*0.01;
        }
        if(from.equals("Square meters")&&to.equals("Hectares")){
            return value*0.0001;
        }
        if(from.equals("Square meters")&&to.equals("Square centimeters")){
            return value*10000;
        }
        if(from.equals("Square meters")&&to.equals("Square feet")){
            return value*10.7639104167;
        }
        if(from.equals("Square meters")&&to.equals("Square inches")){
            return value*1550.0031000062;
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