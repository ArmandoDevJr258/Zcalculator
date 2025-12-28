package com.example.zcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zcalculator.quickies.Area;
import com.example.zcalculator.quickies.Data;
import com.example.zcalculator.quickies.Length;
import com.example.zcalculator.quickies.Tempeture;
import com.example.zcalculator.quickies.Time;

public class Quickies extends AppCompatActivity {
    private ImageButton btnReturn;
    private LinearLayout cardTemperature,cardArea,cardTime,cardMass,cardLenght,cardSpeed,cardTip,cardVolume,cardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quickies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnReturn= findViewById(R.id.btnReturn);
        cardTemperature = findViewById(R.id.cardTemperature);
        cardArea = findViewById(R.id.cardArea);
        cardLenght = findViewById(R.id.cardLenght);
        cardTime = findViewById(R.id.cardTime);
        cardSpeed = findViewById(R.id.cardSpeed);
        cardVolume = findViewById(R.id.cardVolume);
        cardData  = findViewById(R.id.cardData);


        //return to home screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, MainActivity.class);
            startActivity(intent);
        }));
        //go too the quick temperature screen
        cardTemperature.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, Tempeture.class);
            startActivity(intent);
        }));
        //go to quick area screen
        cardArea.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, Area.class);
            startActivity(intent);
        }));
        //go to quick lenght screen
        cardLenght.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, Length.class);
            startActivity(intent);
        }));
        //go to quick time screen
        cardTime.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, Time.class);
            startActivity(intent);
        }));
        //go to quick data scren
        cardData.setOnClickListener((e->{
            Intent intent = new Intent(Quickies.this, Data.class);
            startActivity(intent);
        }));

    }
}