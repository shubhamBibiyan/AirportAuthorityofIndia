package com.example.airportauthorityofindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRGenerator extends AppCompatActivity {
    public EditText et1;
    Button b1, b2;
    ImageView img;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    long maxID = 10;
    public String  qrCodeID;
    Products pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        et1 = findViewById(R.id.et1);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        img = findViewById(R.id.img);
        pro = new Products();

        databaseReference = firebaseDatabase.getInstance().getReference().child("Product");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxID = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = et1.getText().toString();
                if (data.isEmpty()) {
                    et1.setError("Value Required. ");
                } else {
                    QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                    try {
                        Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                        img.setImageBitmap(qrBits);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }

                QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
                try {
                    Bitmap qrBits = qrgEncoder.encodeAsBitmap();
                    img.setImageBitmap(qrBits);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productID = et1.getText().toString();
                if(productID.isEmpty()){
                    et1.setError("Value Required");
                }else{
                    pro.setProId(productID);
                    qrCodeID = productID;
                    databaseReference.child(String.valueOf(qrCodeID)).setValue(pro);
                    Toast.makeText(QRGenerator.this, "Data is successfully added.",Toast.LENGTH_SHORT).show();
                    String passedQr = et1.getText().toString();
                    Intent intent = new Intent(QRGenerator.this, Product_details.class);
                    intent.putExtra("Passed_id", passedQr);
                    startActivity(intent);
                }
            }
        });
    }
}

