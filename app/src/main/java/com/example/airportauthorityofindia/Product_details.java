package com.example.airportauthorityofindia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class Product_details extends AppCompatActivity {
    TextView mregisteration_of_product;
    EditText mproduct_id, mproduct_name, mproduct_description, mproduct_doinstall;
    Button mproduct_registerd;
    DatabaseReference databaseReference;
    Products pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        mregisteration_of_product   = findViewById(R.id.Product_registeration);
        mproduct_id                 = findViewById(R.id.product_id);
        mproduct_name               = findViewById(R.id.product_name);
        mproduct_description        = findViewById(R.id.product_description);
        mproduct_doinstall          = findViewById(R.id.product_doinstall);
        mproduct_registerd          = findViewById(R.id.product_registed);

        mproduct_registerd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proName, proDescription,proDoinstall;
                proName         = mproduct_name.getText().toString().trim();
                proDescription  = mproduct_description.getText().toString().trim();
                proDoinstall    = mproduct_doinstall.getText().toString().trim();
                if (TextUtils.isEmpty(proName)){
                Toast.makeText(Product_details.this, "Please enter product name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(proDescription)){
                    Toast.makeText(Product_details.this, "Please enter description of the product", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(proDoinstall)){
                Toast.makeText(Product_details.this, "Please enter when it is installed.", Toast.LENGTH_SHORT).show();
                }


                pro.setProName(proName);
                pro.setProDescription(proDescription);
                pro.setProDoinstall(proDoinstall);
                databaseReference.child(String.valueOf()).setValue(pro);
            }
        });
    }
}
