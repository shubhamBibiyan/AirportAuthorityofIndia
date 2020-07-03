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

public class Management extends AppCompatActivity {

    public static TextView mgmtresultTextView;
    TextView mgmtdb_Productid, mgmtdb_Productname, mgmtdb_Productdesc, mgmtdb_Dateofinstall, mgmtcustomsearch;
    Button mgmt_scan_btn, mgmt_find_item, mgmt_btn_gen;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        //Scanning QR code
        mgmtresultTextView = findViewById(R.id.mgresult_text);
        mgmt_scan_btn       = findViewById(R.id.mgbtn_scan);
        mgmt_btn_gen       = findViewById(R.id.mgbtn_gen);

        mgmt_scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startActivity(new Intent(getApplication(),ScanCodeActivity2.class));
            }
        });

        mgmt_btn_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(),QRGenerator.class ));
            }
        });



        //Retrieving data from DB
        mgmtdb_Productid       = findViewById(R.id.mgdb_Productid);
        mgmtdb_Productname     = findViewById(R.id.mgdb_Productname);
        mgmtdb_Productdesc     = findViewById(R.id.mgdb_Productdesc);
        mgmtdb_Dateofinstall   = findViewById(R.id.mgdb_Dateofinstall);
        mgmt_find_item          = findViewById(R.id.mgfind_item);
        mgmtcustomsearch       = findViewById(R.id.mgresult_text);


        mgmt_find_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searcheditem = mgmtcustomsearch.getText().toString();
                if (TextUtils.isEmpty(searcheditem)) {
                    Toast.makeText(Management.this, "Please Scan the QR code to get the information", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Product").child(searcheditem);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String productid = dataSnapshot.child("proId").getValue().toString();
                            String productname = dataSnapshot.child("proName").getValue().toString();
                            String productDesc = dataSnapshot.child("proDescription").getValue().toString();
                            String productinstall = dataSnapshot.child("proDoinstall").getValue().toString();

                            mgmtdb_Productid.setText(productid);
                            mgmtdb_Productname.setText(productname);
                            mgmtdb_Productdesc.setText(productDesc);
                            mgmtdb_Dateofinstall.setText(productinstall);
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
