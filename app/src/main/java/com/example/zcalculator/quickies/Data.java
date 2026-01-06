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

public class Data extends AppCompatActivity {
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
        setContentView(R.layout.activity_data);
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


        String[] dataUnits1={
                "Bits",
                "Bytes",
                "Kilobytes",
                "Kibibytes",
                "Megabytes",
                "Mebibytes",
                "Gigabytes",
                "Gibibytes",
                "Terabytes",
                "Tebibytes"

        };

        String[] dataUnits2={
                "Bits",
                "Bytes",
                "Kilobytes",
                "Kibibytes",
                "Megabytes",
                "Mebibytes",
                "Gigabytes",
                "Gibibytes",
                "Terabytes",
                "Tebibytes"

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

        ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.spinner_item,dataUnits1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 =new ArrayAdapter<>(this, R.layout.spinner_item,dataUnits2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //reqturn to Quickies screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Data.this, Quickies.class);
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

                double result = convertData(value,fromUnit,toUnit);
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

                double result= convertData(value,fromUnit,toUnit);
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
                if (selectedItem.equals("Bits")){
                    unitTxt1.setText("bit");




                } else if (selectedItem.equals("Bytes")) {
                    unitTxt1.setText("B");

                }else if (selectedItem.equals("Kilobytes")) {
                    unitTxt1.setText("KB");

                }
                else if (selectedItem.equals("Kibibytes")) {
                    unitTxt1.setText("KiB");

                }
                else if (selectedItem.equals("Megabytes")) {
                    unitTxt1.setText("MB");

                }
                else if (selectedItem.equals("Mebibytes")) {
                    unitTxt1.setText("MiB");

                }
                else if (selectedItem.equals("Gigabytes")) {
                    unitTxt1.setText("GB");

                }
                else if (selectedItem.equals("Gibibytes")) {
                    unitTxt1.setText("GiB");

                }
                else if (selectedItem.equals("Terabytes")) {
                    unitTxt1.setText("TB");

                }
                else if (selectedItem.equals("Tebibytes")) {
                    unitTxt1.setText("TiB");

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
                if (selectedItem.equals("Bits")){
                    unitTxt2.setText("bit");




                } else if (selectedItem.equals("Bytes")) {
                    unitTxt2.setText("B");

                }
                else if (selectedItem.equals("Kilobytes")) {
                    unitTxt2.setText("KB");

                }else if (selectedItem.equals("Kibibytes")) {
                    unitTxt2.setText("KiB");

                }
                else if (selectedItem.equals("Megabytes")) {
                    unitTxt2.setText("MB");

                }
                else if (selectedItem.equals("Mebibytes")) {
                    unitTxt2.setText("MiB");

                }
                else if (selectedItem.equals("Gigabytes")) {
                    unitTxt2.setText("GB");

                }
                else if (selectedItem.equals("Gibibytes")) {
                    unitTxt2.setText("GiB");

                }
                else if (selectedItem.equals("Terabytes")) {
                    unitTxt2.setText("TB");

                }
                else if (selectedItem.equals("Tebibytes")) {
                    unitTxt2.setText("TiB");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private  double convertData(double value,String from,String to){


        if (from.equals(to)){
            return value;
        }
        //from Bits
        if(from.equals("Bits")&&to.equals("Bytes")){
            return value*0.125;
        }
        if(from.equals("Bits")&&to.equals("Kilobytes")){
            return value*0.000125;
        }
        if(from.equals("Bits")&&to.equals("Kibibytes")){
            return value*0.0001220703;
        }
        if(from.equals("Bits")&&to.equals("Megabytes")){
            return value*1.25000000E-7;
        }
        if(from.equals("Bits")&&to.equals("Mebibytes")){
            return value*1.19209290E-7;
        }
        if(from.equals("Bits")&&to.equals("Gigabytes")){
            return value*1.25000000E-10;
        }
        if(from.equals("Bits")&&to.equals("Gibibytes")){
            return value*1.16415322E-10;
        }
        if(from.equals("Bits")&&to.equals("Terabytes")){
            return value*1.25000000E-13;
        }
        if(from.equals("Bits")&&to.equals("Tebibytes")){
            return value*1.13686838E-13;
        }
        //from Bytes
        if(from.equals("Bytes")&&to.equals("Kilobytes")){
            return value*0.001;
        }
        if(from.equals("Bytes")&&to.equals("Kibibytes")){
            return value*0.0009765625;
        }
        if(from.equals("Bytes")&&to.equals("Megabytes")){
            return value*0.000001;
        }
        if(from.equals("Bytes")&&to.equals("Mebibytes")){
            return value*9.53674316E-7;
        }
        if(from.equals("Bytes")&&to.equals("Gigabytes")){
            return value*1.00000000E-9;
        }
        if(from.equals("Bytes")&&to.equals("Gibibytes")){
            return value*9.31322575E-10;
        }
        if(from.equals("Bytes")&&to.equals("Terabytes")){
            return value*1.00000000E-12;
        }
        if(from.equals("Bytes")&&to.equals("Tebibytes")){
            return value*9.09494702E-13;
        }

        //from kilobytes
        if(from.equals("Kilobytes")&&to.equals("Bits")){
            return value*8000;
        }
        if(from.equals("Kilobytes")&&to.equals("Bytes")){
            return value*1000;
        }
        if(from.equals("Kilobytes")&&to.equals("Kibibytes")){
            return value*0.9765625;
        }
        if(from.equals("Kilobytes")&&to.equals("Megabytes")){
            return value*0.001;
        }
        if(from.equals("Kilobytes")&&to.equals("Mebibytes")){
            return value*0.0009536743;
        }
        if(from.equals("Kilobytes")&&to.equals("Gigabytes")){
            return value*0.000001;
        }
        if(from.equals("Kilobytes")&&to.equals("Gibibytes")){
            return value*9.31322575E-7;
        }
        if(from.equals("Kilobytes")&&to.equals("Terabytes")){
            return value*1.00000000E-9;
        }
        if(from.equals("Kilobytes")&&to.equals("Tebibytes")){
            return value*9.09494702E-10;
        }

        //from Kibibytes
        if(from.equals("Kibibytes")&&to.equals("Bits")){
            return value*8192;
        }
        if(from.equals("Kibibytes")&&to.equals("Bytes")){
            return value*1024;
        }
        if(from.equals("Kibibytes")&&to.equals("Kilobytes")){
            return value*1024;
        }
        if(from.equals("Kibibytes")&&to.equals("Megabytes")){
            return value*0.001024;
        }
        if(from.equals("Kibibytes")&&to.equals("Mebibytes")){
            return value*0.0009765625;
        }
        if(from.equals("Kibibytes")&&to.equals("Gigabytes")){
            return value*0.000001024;
        }
        if(from.equals("Kibibytes")&&to.equals("Gibibytes")){
            return value*9.53674316E-7;
        }
        if(from.equals("Kibibytes")&&to.equals("Terabytes")){
            return value*1.02400000E-9;
        }
        if(from.equals("Kibibytes")&&to.equals("Tebibytes")){
            return value*8192;
        }

        //from megabytes
        if(from.equals("Megabytes")&&to.equals("Bits")){
            return value*8000000;
        }
        if(from.equals("Megabytes")&&to.equals("Bytes")){
            return value*1000000;
        }
        if(from.equals("Megabytes")&&to.equals("Kilobytes")){
            return value*1000;
        }
        if(from.equals("Megabytes")&&to.equals("Kibibytes")){
            return value*976.5625;
        }
        if(from.equals("Megabytes")&&to.equals("Mebibytes")){
            return value*0.9536743164;
        }
        if(from.equals("Megabytes")&&to.equals("Gigabytes")){
            return value*0.001;
        }
        if(from.equals("Megabytes")&&to.equals("Gibibytes")){
            return value*0.0009313226;
        }
        if(from.equals("Megabytes")&&to.equals("Terabytes")){
            return value*0.000001;
        }
        if(from.equals("Megabytes")&&to.equals("Bits")){
            return value*9.09494702E-7;
        }

        //from Mebibytes
        if(from.equals("Mebibytes")&&to.equals("Bits")){
            return value*8388608;
        }
        if(from.equals("Mebibytes")&&to.equals("Bytes")){
            return value*1048576;
        }
        if(from.equals("Mebibytes")&&to.equals("Kilobytes")){
            return value*1048576;
        }
        if(from.equals("Mebibytes")&&to.equals("Kibibytes")){
            return value*1024;
        }
        if(from.equals("Mebibytes")&&to.equals("Megabytes")){
            return value*1.048576;
        }
        if(from.equals("Mebibytes")&&to.equals("Gigabytes")){
            return value*0.001048576;
        }
        if(from.equals("Mebibytes")&&to.equals("Gibibytes")){
            return value*0.0009765625;
        }
        if(from.equals("Mebibytes")&&to.equals("Terabytes")){
            return value*0.0000010486;
        }
        if(from.equals("Mebibytes")&&to.equals("Bits")){
            return value*9.53674316E-7;
        }

        //from Gigabytes
//        if(from.equals("Gigabytes")&&to.equals("Bits")){
//            return v
//        }
        if(from.equals("Gigabytes")&&to.equals("Bytes")){
            return value*1000000000;
        }
        if(from.equals("Gigabytes")&&to.equals("Kilobytes")){
            return value*1000000;
        }
        if(from.equals("Gigabytes")&&to.equals("Kibibytes")){
            return value*976562.5;
        }
        if(from.equals("Gigabytes")&&to.equals("Megabytes")){
            return value*1000;
        }
        if(from.equals("Gigabytes")&&to.equals("Mebibytes")){
            return value*953.6743164063;
        }
        if(from.equals("Gigabytes")&&to.equals("Terabytes")){
            return value*0.001;
        }
        if(from.equals("Gigabytes")&&to.equals("Tebibytes")){
            return value*0.0009094947;
        }

        //from Gibibytes
//        if(from.equals("Gibibytes")&&to.equals("Bits")){
//            return value*8589934592;
//        }
        if(from.equals("Gibibytes")&&to.equals("Bytes")){
            return value*1073741824;
        }
        if(from.equals("Gibibytes")&&to.equals("Kilobytes")){
            return value*1073741824;
        }
        if(from.equals("Gibibytes")&&to.equals("Kibibytes")){
            return value*1048576;
        }
        if(from.equals("Gibibytes")&&to.equals("Megabytes")){
            return value*1073.741824;
        }
        if(from.equals("Gibibytes")&&to.equals("Mebibytes")){
            return value*1024;
        }
        if(from.equals("Gibibytes")&&to.equals("Gigabytes")){
            return value*1.073741824;
        }
        if(from.equals("Gibibytes")&&to.equals("Gibibytes")){
            return value*1;
        }
        if(from.equals("Gibibytes")&&to.equals("Terabytes")){
            return value*0.0010737418;
        }
        if(from.equals("Gibibytes")&&to.equals("Tebibytes")){
            return value*0.0009765625;
        }

        //from Terabytes
//        if(from.equals("Terabytes")&&to.equals("Bits")){
//            return value*1073741824;
//        }
//        if(from.equals("Terabytes")&&to.equals("Bytes")){
//            return value*1073741824;
//        }
        if(from.equals("Terabytes")&&to.equals("Kilobytes")){
            return value*1000000000;
        }
        if(from.equals("Terabytes")&&to.equals("Kibibytes")){
            return value*976562500;
        }
        if(from.equals("Terabytes")&&to.equals("Megabytes")){
            return value*1000000;
        }
        if(from.equals("Terabytes")&&to.equals("Mebibytes")){
            return value*953674.31640625;
        }
        if(from.equals("Terabytes")&&to.equals("Gigabytes")){
            return value*1000;
        }
        if(from.equals("Terabytes")&&to.equals("Gibibytes")){
            return value*931.3225746155;
        }
        if(from.equals("Terabytes")&&to.equals("Tebibytes")){
            return value*0.9094947018;
        }

        //from Tebibytes
        if(from.equals("Tebibytes")&&to.equals("Kibibytes")){
            return value*1073741824;
        }
        if(from.equals("Tebibytes")&&to.equals("Megabytes")){
            return value*1099511.627776;
        }
        if(from.equals("Tebibytes")&&to.equals("Mebibytes")){
            return value*1048576;
        }
        if(from.equals("Tebibytes")&&to.equals("Gigabytes")){
            return value*1099.511627776;
        }
        if(from.equals("Tebibytes")&&to.equals("Gibibytes")){
            return value*1024;
        }
        if(from.equals("Tebibytes")&&to.equals("Terabytes")){
            return value*1.0995116278;
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