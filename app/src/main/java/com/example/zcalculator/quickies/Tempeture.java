package com.example.zcalculator.quickies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Tempeture extends AppCompatActivity {
    private Spinner spinner1,spinner2;
    private ImageButton btnReturn;
    private TextView unitTxt1,unitTxt2;
    private EditText input1,input2;

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

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem =adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Celsius")){
                    unitTxt1.setText("ºC");




                } else if (selectedItem.equals("Kelvin")) {
                    unitTxt1.setText("K");

                }else if (selectedItem.equals("Fahrenheit")) {
                    unitTxt1.setText("ºF");

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

                } else if (selectedItem.equals("Kelvin")) {
                    unitTxt2.setText("K");

                }
                else if (selectedItem.equals("Fahrenheit")) {
                    unitTxt2.setText("ºF");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private  void Celsius(){


    }
}