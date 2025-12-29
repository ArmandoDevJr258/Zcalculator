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

public class Length extends AppCompatActivity {
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
        setContentView(R.layout.activity_length);
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

        String[] lengthUnits1 ={
                "Millimeters",
                "Centimeters",
                "Meters",
                "Kilometers",
                "Inches",
                "Feet",
                "Yards",
                "Miltes",
                "Nautical miles",
                "mils"

        };
        String[] lengthUnits2 ={
                "Millimeters",
                "Centimeters",
                "Meters",
                "Kilometers",
                "Inches",
                "Feet",
                "Yards",
                "Miltes",
                "Nautical miles",
                "mils"

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

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,lengthUnits1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 =new ArrayAdapter<>(this, R.layout.spinner_item,lengthUnits2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //return to Quickies Screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Length.this, Quickies.class);
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

                double result = convertLength(value,fromUnit,toUnit);
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

                double result= convertLength(value,fromUnit,toUnit);
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
                String selectedItem =adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Millimeters")){
                    unitTxt1.setText("mm");




                } else if (selectedItem.equals("Centimeters")) {
                    unitTxt1.setText("cm");

                }else if (selectedItem.equals("Meters")) {
                    unitTxt1.setText("m");

                }
                else if (selectedItem.equals("Kilometers")) {
                    unitTxt1.setText("km");

                }
                else if (selectedItem.equals("Inches")) {
                    unitTxt1.setText("in");

                }
                else if (selectedItem.equals("Feet")) {
                    unitTxt1.setText("ft");

                }
                else if (selectedItem.equals("Yards")) {
                    unitTxt1.setText("yd");

                }
                else if (selectedItem.equals("Miles")) {
                    unitTxt1.setText("mi");

                }
                else if (selectedItem.equals("Nautical miles")) {
                    unitTxt1.setText("NM");

                }
                else if (selectedItem.equals("Mils")) {
                    unitTxt1.setText("mil");

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
                if (selectedItem.equals("Millimeters")){
                    unitTxt2.setText("mm");




                } else if (selectedItem.equals("Centimeters")) {
                    unitTxt2.setText("cm");

                }else if (selectedItem.equals("Meters")) {
                    unitTxt2.setText("m");

                }
                else if (selectedItem.equals("Kilometers")) {
                    unitTxt2.setText("km");

                }
                else if (selectedItem.equals("Inches")) {
                    unitTxt2.setText("in");

                }
                else if (selectedItem.equals("Feet")) {
                    unitTxt2.setText("ft");

                }
                else if (selectedItem.equals("Yards")) {
                    unitTxt2.setText("yd");

                }
                else if (selectedItem.equals("Miles")) {
                    unitTxt2.setText("mi");

                }
                else if (selectedItem.equals("Nautical miles")) {
                    unitTxt2.setText("NM");

                }
                else if (selectedItem.equals("Mils")) {
                    unitTxt2.setText("mil");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private  double convertLength(double value,String from,String to){


        if (from.equals(to)){
            return value;
        }

        //from Millimeters
        if(from.equals("Millimeters")&&to.equals("Centimeters")){
            return value*0.1;
        }
        if(from.equals("Millimeters")&&to.equals("Meters")){
            return value*0.001;
        }
        if(from.equals("Millimeters")&&to.equals("Kilometers")){
            return value*0.000001;
        }
        if(from.equals("Millimeters")&&to.equals("Inches")){
            return value*0.0393700787;
        }
        if(from.equals("Millimeters")&&to.equals("Feet")){
            return value*0.0032808399;
        }
        if(from.equals("Millimeters")&&to.equals("Yards")){
            return value*0.0010936133;
        }
        if(from.equals("Millimeters")&&to.equals("Miles")){
            return value*6.21371192E-7;
        }
        if(from.equals("Millimeters")&&to.equals("Nautical miles")){
            return value*5.39956803E-7;
        }
        if(from.equals("Millimeters")&&to.equals("Mils")){
            return value*39.3700787402;
        }

        //from Centimeters
        if(from.equals("Centimeters")&&to.equals("Millimeters")){
            return value*10;
        }
        if(from.equals("Centimeters")&&to.equals("Meters")){
            return value*0.01;
        }
        if(from.equals("Centimeters")&&to.equals("Kilometers")){
            return value*0.00001;
        }
        if(from.equals("Centimeters")&&to.equals("Inches")){
            return value*0.3937007874;
        }
        if(from.equals("Centimeters")&&to.equals("Feet")){
            return value*0.032808399;
        }
        if(from.equals("Centimeters")&&to.equals("Yards")){
            return value*0.010936133;
        }
        if(from.equals("Centimeters")&&to.equals("Miles")){
            return value*0.0000062137;
        }
        if(from.equals("Centimeters")&&to.equals("Nautical miles")){
            return value*0.0000053996;
        }

        //from Meters
        if(from.equals("Meters")&&to.equals("Millimeters")){
            return value*1000;
        }
        if(from.equals("Meters")&&to.equals("Centimeters")){
            return value*100;
        }
        if(from.equals("Meters")&&to.equals("Kilometers")){
            return value*0.001;
        }
        if(from.equals("Meters")&&to.equals("Inches")){
            return value*39.3700787402;
        }
        if(from.equals("Meters")&&to.equals("Feet")){
            return value*3.280839895;
        }
        if(from.equals("Meters")&&to.equals("Yards")){
            return value*1.0936132983;
        }
        if(from.equals("Meters")&&to.equals("Miles")){
            return value*0.0006213712;
        }
        if(from.equals("Meters")&&to.equals("Nautical miles")){
            return value*0.0005399568;
        }
        if(from.equals("Meters")&&to.equals("Mils")){
            return value*39370.078740157;
        }

        //from Kilometers
        if(from.equals("Kilometers")&&to.equals("Millimeters")){
            return value*1000000;
        }
        if(from.equals("Kilometers")&&to.equals("Centimeters")){
            return value*100000;
        }
        if(from.equals("Kilometers")&&to.equals("Meters")){
            return value*1000;
        }
        if(from.equals("Kilometers")&&to.equals("Inches")){
            return value*39370.078740157;
        }
        if(from.equals("Kilometers")&&to.equals("Feet")){
            return value*3280.8398950131;
        }
        if(from.equals("Kilometers")&&to.equals("Yards")){
            return value*1093.6132983377;
        }
        if(from.equals("Kilometers")&&to.equals("Miles")){
            return value*0.6213711922;
        }
        if(from.equals("Kilometers")&&to.equals("Nautical miles")){
            return value*0.5399568035;
        }
        if(from.equals("Kilometers")&&to.equals("Mils")){
            return value*39370078.740157;
        }

        //from Inches
        if(from.equals("Inches")&&to.equals("Millimeters")){
            return value*25.4;
        }
        if(from.equals("Inches")&&to.equals("Centimeters")){
            return value*2.54;
        }
        if(from.equals("Inches")&&to.equals("Meters")){
            return value*0.0254;
        }
        if(from.equals("Inches")&&to.equals("Kilometers")){
            return value*0.0000254;
        }
        if(from.equals("Inches")&&to.equals("Feet")){
            return value*0.8333333333;
        }
        if(from.equals("Inches")&&to.equals("Yards")){
            return value*0.0277777778;
        }
        if(from.equals("Inches")&&to.equals("Miles")){
            return value*0.0000157828;
        }
        if(from.equals("Inches")&&to.equals("Nautical miles")){
            return value*0.0000137149;
        }
        if(from.equals("Inches")&&to.equals("Mils")){
            return value*1000;
        }

        //from Feet
        if(from.equals("Feet")&&to.equals("Millimeters")){
            return value*304.8;
        }
        if(from.equals("Feet")&&to.equals("Centimeters")){
            return value*30.48;
        }
        if(from.equals("Feet")&&to.equals("Meters")){
            return value*0.3048;
        }
        if(from.equals("Feet")&&to.equals("Kilometers")){
            return value*0.0003048;
        }
        if(from.equals("Feet")&&to.equals("Inches")){
            return value*12;
        }
        if(from.equals("Feet")&&to.equals("Yards")){
            return value*0.33333333333;
        }
        if(from.equals("Feet")&&to.equals("Miles")){
            return value*0.0001893939;
        }
        if(from.equals("Feet")&&to.equals("Nautical miles")){
            return value*0.0001645788;
        }
        if(from.equals("Feet")&&to.equals("Mils")){
            return value*12000;
        }

        //from Yards
        if(from.equals("Yards")&&to.equals("Millimeters")){
            return value*914.4;
        }
        if(from.equals("Yards")&&to.equals("Centimeters")){
            return value*91.44;
        }
        if(from.equals("Yards")&&to.equals("Meters")){
            return value*0.9144;
        }
        if(from.equals("Yards")&&to.equals("Kilometers")){
            return value*0.0009144;
        }
        if(from.equals("Yards")&&to.equals("Inches")){
            return value*36;
        }
        if(from.equals("Yards")&&to.equals("Feet")){
            return value*3;
        }
        if(from.equals("Yards")&&to.equals("Miles")){
            return value*0.0005681818;
        }
        if(from.equals("Yards")&&to.equals("Nautical miles")){
            return value*0.0004937365;
        }
        if(from.equals("Yards")&&to.equals("Mils")){
            return value*36000;
        }

        //from Miles
        if(from.equals("Miles")&&to.equals("Millimeters")){
            return value*1609344;
        }
        if(from.equals("Miles")&&to.equals("Centimeters")){
            return value*160934.4;
        }
        if(from.equals("Miles")&&to.equals("Meters")){
            return value*1609.344;
        }
        if(from.equals("Miles")&&to.equals("Kilometers")){
            return value*1.609344;
        }
        if(from.equals("Miles")&&to.equals("Inches")){
            return value*63360;
        }
        if(from.equals("Miles")&&to.equals("Feet")){
            return value*5280;
        }
        if(from.equals("Miles")&&to.equals("Yards")){
            return value*1760;
        }
        if(from.equals("Miles")&&to.equals("Nautical miles")){
            return value*0.8689762419;
        }
        if(from.equals("Miles")&&to.equals("Mils")){
            return value*63360000;
        }

        //from Nautical Miles
        if(from.equals("Nautical miles")&&to.equals("Millimeters")){
            return value*1852000;
        }
        if(from.equals("Nautical miles")&&to.equals("Centimeters")){
            return value*185200;
        }
        if(from.equals("Nautical miles")&&to.equals("Meters")){
            return value*1852;
        }
        if(from.equals("Nautical miles")&&to.equals("Kilometers")){
            return value*1.852;
        }
        if(from.equals("Nautical miles")&&to.equals("Inches")){
            return value*72913.385826772;
        }
        if(from.equals("Nautical miles")&&to.equals("Feet")){
            return value*6076.1154855643;
        }
        if(from.equals("Nautical miles")&&to.equals("Yards")){
            return value*202285214;
        }
        if(from.equals("Nautical miles")&&to.equals("Miles")){
            return value*1.150779448;
        }
        if(from.equals("Nautical miles")&&to.equals("Mils")){
            return value*72913385.826772;
        }

        //from Mils
        if(from.equals("Mils")&&to.equals("Millimeters")){
            return value*0.0254;
        }
        if(from.equals("Mils")&&to.equals("Centimeters")){
            return value*0.00254;
        }
        if(from.equals("Mils")&&to.equals("Meters")){
            return value*0.0000254;
        }
        if(from.equals("Mils")&&to.equals("Kilometers")){
            return value*2.54000000E-8;
        }
        if(from.equals("Mils")&&to.equals("Inches")){
            return value*0.001;
        }
        if(from.equals("Mils")&&to.equals("Feet")){
            return value*0.0000833333;
        }
        if(from.equals("Mils")&&to.equals("Yards")){
            return value*0.0000277778;
        }
        if(from.equals("Mils")&&to.equals("Miles")){
            return value*1.57828283E-8;
        }
        if(from.equals("Mils")&&to.equals("Nautical miles")){
            return value*1.371490028E-8;
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