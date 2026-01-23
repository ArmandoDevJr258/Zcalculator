package com.example.zcalculator;

import static android.view.View.VISIBLE;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Sampler;
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

import java.io.OutputStream;
import java.util.ArrayList;

public class FunctionToGraph extends AppCompatActivity {

    private ImageButton showkeyPad,btnGenerate,btnReturn,btnresize,btnTakeScreenshot;
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
        btnReturn= findViewById(R.id.btnReturn);
        btnresize= findViewById(R.id.btnresize);
        btnTakeScreenshot= findViewById(R.id.btnTakeScreenshot);


        functionInput.setOnClickListener((e->{


            showkeyPad.setVisibility(VISIBLE);
        }));
        setupChart();

        btnGenerate.setOnClickListener(v -> plotGraph());

        //return to the Main Graphs screen
        btnReturn.setOnClickListener((e->{
            Intent intent = new Intent(FunctionToGraph.this, Graphs.class);
            startActivity(intent);
        }));
        btnresize.setOnClickListener((e->{
            lineChart.fitScreen();
            lineChart.invalidate();
        }));
        btnTakeScreenshot.setOnClickListener((e->{
            lineChart.setDrawingCacheEnabled(true);
            lineChart.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(lineChart.getDrawingCache());
            lineChart.setDragDecelerationEnabled(false);
            saveBitmap(bitmap);
        }));
    }
    private void setupChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getX();


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisLineColor(Color.GREEN);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLUE);

        YAxis left = lineChart.getAxisLeft();
       left.setDrawLabels(true);
        left.setTextSize(12f);
        left.setTextColor(Color.BLUE);


        left.setDrawGridLines(true);
        lineChart.setNoDataText("No function added yet");



        lineChart.getAxisRight().setEnabled(true);
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
            dataSet.setDrawCircles(false);
            dataSet.setDrawCircleHole(false);
            dataSet.setMode(LineDataSet.Mode.LINEAR);
            dataSet.setLineWidth(4.5f);
            dataSet.setDrawValues(false);
            dataSet.setHighlightEnabled(false);
            dataSet.setColor(Color.GREEN);
            dataSet.setDrawVerticalHighlightIndicator(true);
            dataSet.setDrawHorizontalHighlightIndicator(true);
            String label = dataSet.getLabel();


            lineChart.setData(data);
            lineChart.animateX(800);
            lineChart.invalidate();

        } catch (Exception e) {
            Toast.makeText(this, "Invalid function", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveBitmap(Bitmap bitmap){
        String filename = "Graph_"+System.currentTimeMillis()+".png";

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename);
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/MyGraphs");

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream out = getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.close();
            Toast.makeText(this, "Screenshot saved!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to save Screenshot", Toast.LENGTH_SHORT).show();
        }

    }
}