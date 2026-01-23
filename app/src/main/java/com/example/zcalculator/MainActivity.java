package com.example.zcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private LinearLayout quickiesCard,mainCalculatorCard,graphCard,scantoSolveCard;
    private ImageButton btnSettings,btnRefresh;
    private TextView txtbuyValue1,txtsellValue1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        applySavedLanguage();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quickiesCard= findViewById(R.id.quickiesCard);
        mainCalculatorCard=findViewById(R.id.mainCalculatorCard);
        graphCard= findViewById(R.id.graphCard);
        scantoSolveCard= findViewById(R.id.scantoSolveCard);
        btnSettings = findViewById(R.id.btnSettings);
        txtbuyValue1 = findViewById(R.id.txtbuyValue1);
        txtsellValue1 = findViewById(R.id.txtsellValue1);
        btnRefresh = findViewById(R.id.btnRefresh);

        fetchDollarRate();

        //open the settings Screen
        btnSettings.setOnClickListener((e->{
//            Intent intent = new Intent(MainActivity.this, Settings.class);
//            startActivity(intent);
            fetchDollarRate();

        }));
        //oepn quickies screen
        quickiesCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, Quickies.class);
            startActivity(intent);

        }));

        //open main calculator screen
        mainCalculatorCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, MainCalculator.class);
            startActivity(intent);
        }));
        //open the Graphs screen
        graphCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, Graphs.class);
            startActivity(intent);
        }));

        //open the Scan to solve  screen
        scantoSolveCard.setOnClickListener((e->{
            Intent intent = new Intent(MainActivity.this, ScanAndSolve.class);
            startActivity(intent);
        }));

        //change language
        btnRefresh.setOnClickListener((e->{
            toggleLanguage();
            recreate();
            applySavedLanguage();
        }));












    }

    private void fetchDollarRate() {

        runOnUiThread(() -> {
            txtbuyValue1.setText("actualizando");
            txtsellValue1.setText("actualizando");
        });
        new Thread(() -> {
            try {
                // USD -> MZN
                String urlStr = "https://api.exchangerate.host/latest?base=USD&symbols=MZN";
                URL url = new URL(urlStr);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder json = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }

                reader.close();
                connection.disconnect();

                JSONObject response = new JSONObject(json.toString());
                JSONObject rates = response.getJSONObject("rates");

                double rate = rates.getDouble("MZN");

                // Simulação de compra e venda
                double buyRate = rate - 1.5;   // compra
                double sellRate = rate + 1.5;  // venda

                runOnUiThread(() -> {
                    txtbuyValue1.setText("Compra USD: " + String.format("%.2f", buyRate) + " MZN");
                    txtsellValue1.setText("Venda USD: " + String.format("%.2f", sellRate) + " MZN");
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void toggleLanguage() {
        SharedPreferences prefs = getSharedPreferences("lang", MODE_PRIVATE);
        String current = prefs.getString("language", "en");

        String newLang = current.equals("en") ? "pt" : "en";
        prefs.edit().putString("language", newLang).apply();

        setLocale(newLang);
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = getSharedPreferences("lang", MODE_PRIVATE);
        String lang = prefs.getString("language", "en");
        setLocale(lang);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }

}