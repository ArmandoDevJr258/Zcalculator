package com.example.zcalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.google.common.util.concurrent.ListenableFuture;

public class ScanAndSolve extends AppCompatActivity {

    private PreviewView previewView;
    private ImageButton btnFlash, btnScan;
    private boolean isFlashOn = false;
    private Camera camera; // to control flashlight
    private ImageAnalysis analysis;
    private TextRecognizer recognizer;
    private ImageProxy lastImageProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan_and_solve);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        previewView = findViewById(R.id.previewView);
        btnFlash = findViewById(R.id.btnFlash);
        btnScan = findViewById(R.id.btnScan);
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 101);
        } else {
            startCamera();
        }



// Flashlight toggle
        btnFlash.setOnClickListener(v -> toggleFlash());

// Scan button (we’ll fill the logic later)
        btnScan.setOnClickListener(v -> manualScan());



    }
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();


                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());


                analysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();


                analysis.setAnalyzer(ContextCompat.getMainExecutor(this), this::analyzeImage);


                CameraSelector selector = CameraSelector.DEFAULT_BACK_CAMERA;


                cameraProvider.unbindAll();
                camera = cameraProvider.bindToLifecycle(this, selector, preview, analysis);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void toggleFlash() {
        if (camera != null && camera.getCameraInfo().hasFlashUnit()) {
            isFlashOn = !isFlashOn;
            camera.getCameraControl().enableTorch(isFlashOn);
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private void analyzeImage(ImageProxy imageProxy) {
        lastImageProxy = imageProxy;

    }
    private void manualScan() {
        if (lastImageProxy == null) return;

        @SuppressLint("UnsafeOptInUsageError")
        Image mediaImage = lastImageProxy.getImage();
        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage,
                    lastImageProxy.getImageInfo().getRotationDegrees());

            recognizer.process(image)
                    .addOnSuccessListener(result -> {
                        String scannedText = result.getText();
                        if (!scannedText.isEmpty()) {

                            scannedText = cleanScannedText(scannedText);
                            sendToScannedActivity(scannedText);
                        }
                    })
                    .addOnCompleteListener(task -> lastImageProxy.close());
        }
    }

    private String cleanScannedText(String text) {
        return text.trim();
    }
    private void sendToScannedActivity(String scannedText) {
        Intent intent = new Intent(this, ScannedExpression.class);
        intent.putExtra("scanned_text", scannedText);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }
    }



}