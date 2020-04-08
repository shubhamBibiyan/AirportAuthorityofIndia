package com.example.airportauthorityofindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Technician extends AppCompatActivity {
    public static TextView resultTextView;
    TextView mdb_Productid, mdb_Productname, mdb_Productdesc, mdb_Dateofinstall, mcustomsearch;
    Button scan_btn, mfind_item, mbtn_gen;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);

        //Scanning QR code
        resultTextView = findViewById(R.id.result_text);
        scan_btn       = findViewById(R.id.btn_scan);
        mbtn_gen       = findViewById(R.id.btn_gen);

        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startActivity(new Intent(getApplication(),ScanCodeActivity.class));
            }
        });

        mbtn_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(),QRGenerator.class ));
            }
        });



        //Retrieving data from DB
        mdb_Productid       = findViewById(R.id.db_Productid);
        mdb_Productname     = findViewById(R.id.db_Productname);
        mdb_Productdesc     = findViewById(R.id.db_Productdesc);
        mdb_Dateofinstall   = findViewById(R.id.db_Dateofinstall);
        mfind_item          = findViewById(R.id.find_item);
        mcustomsearch       = findViewById(R.id.result_text);


        mfind_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searcheditem = mcustomsearch.getText().toString();
                if (TextUtils.isEmpty(searcheditem)) {
                    Toast.makeText(Technician.this, "Please Scan the QR code to get the information", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Product").child(searcheditem);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String productid = dataSnapshot.child("ProductID").getValue().toString();
                            String productname = dataSnapshot.child("ProductName").getValue().toString();
                            String productDesc = dataSnapshot.child("ProductDesc").getValue().toString();
                            String productinstall = dataSnapshot.child("InstallDate").getValue().toString();

                            mdb_Productid.setText(productid);
                            mdb_Productname.setText(productname);
                            mdb_Productdesc.setText(productDesc);
                            mdb_Dateofinstall.setText(productinstall);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });


    }
}
