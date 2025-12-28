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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zcalculator.Quickies;
import com.example.zcalculator.R;

public class Area extends AppCompatActivity {
    private Spinner spinner1,spinner2;
    private ImageButton btnReturn;
    private TextView unitTxt1,unitTxt2;
    private EditText input1,input2;

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

        spinner1= findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btnReturn = findViewById(R.id.btnReturn);
        unitTxt1 = findViewById(R.id.unitTxt1);
        unitTxt2 = findViewById(R.id.unitTxt2);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

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
}