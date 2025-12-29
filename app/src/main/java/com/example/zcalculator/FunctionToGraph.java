package com.example.zcalculator;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

public class FunctionToGraph extends AppCompatActivity {

    private ImageButton showkeyPad,btnGenerate;
    private EditText functionInput,activeInput;


     private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_function_to_graph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        showkeyPad = findViewById(R.id.showkeyPad);
        functionInput= findViewById(R.id.functionInput);
        lineChart = findViewById(R.id.lineChart);
        btnGenerate = findViewById(R.id.btnGenerate);


        functionInput.setOnClickListener((e->{


            showkeyPad.setVisibility(VISIBLE);
        }));
        setupChart();

        btnGenerate.setOnClickListener(v -> plotGraph());
    }
    private void setupChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis left = lineChart.getAxisLeft();
        left.setDrawGridLines(true);

        lineChart.getAxisRight().setEnabled(false);
    }

    private void plotGraph() {
        String input = functionInput.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Enter a function", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ArrayList<Entry> entries = new ArrayList<>();

            Expression expression = new ExpressionBuilder(input)
                    .variable("x")
                    .build();

            for (double x = -10; x <= 10; x += 0.1) {
                expression.setVariable("x", x);
                double y = expression.evaluate();

                if (Double.isFinite(y)) {
                    entries.add(new Entry((float) x, (float) y));
                }
            }

            LineDataSet dataSet = new LineDataSet(entries, "y = " + input);
            LineData data = new LineData(dataSet);

            lineChart.setData(data);
            lineChart.invalidate();

        } catch (Exception e) {
            Toast.makeText(this, "Invalid function", Toast.LENGTH_SHORT).show();
        }
    }
}