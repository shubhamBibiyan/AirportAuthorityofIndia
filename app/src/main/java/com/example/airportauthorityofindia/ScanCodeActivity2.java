package com.example.airportauthorityofindia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(ScanCodeActivity2.this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result2){
        Management.mgmtresultTextView.setText(result2.getText());
        onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(ScanCodeActivity2.this);
        ScannerView.startCamera();
    }
}
