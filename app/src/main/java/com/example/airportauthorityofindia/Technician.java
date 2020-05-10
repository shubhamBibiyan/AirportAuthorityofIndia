package com.example.airportauthorityofindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Technician extends AppCompatActivity {
    public static TextView resultTextView;
    TextView mdb_Productid, mdb_Productname, mdb_Productdesc, mdb_Dateofinstall, mcustomsearch;
    EditText maintainedOn_tech, problem_desc_tech;
    Button scan_btn, mfind_item, submit_tech;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    maintainance maintainanceReport;
    long max = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);

        //Scanning QR code
        resultTextView = findViewById(R.id.result_text);
        scan_btn       = findViewById(R.id.btn_scan);

        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startActivity(new Intent(getApplication(),ScanCodeActivity.class));
            }
        });


        maintainedOn_tech = findViewById(R.id.maintainedOn);
        problem_desc_tech = findViewById(R.id.problemfixed);
        submit_tech = findViewById(R.id.submit_report);
        maintainanceReport = new maintainance();

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

        submit_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String current_machine = mcustomsearch.getText().toString();
                databaseReference = firebaseDatabase.getInstance().getReference().child("Product");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String dateOfRepair = maintainedOn_tech.getText().toString();
                String problemDescription = problem_desc_tech.getText().toString();

                if(TextUtils.isEmpty(dateOfRepair)){
                    Toast.makeText(Technician.this, "Please Enter date", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(problemDescription)){
                    Toast.makeText(Technician.this, "Please Enter Problem fixed by you", Toast.LENGTH_SHORT).show();
                }
                maintainanceReport.setDateOfRepair(dateOfRepair);
                maintainanceReport.setProblem_description(problemDescription);
                max = max +1;
                databaseReference.child(String.valueOf(current_machine)).child(String.valueOf("maintanceRecord")).child(String.valueOf(max)).setValue(maintainanceReport);

            }
        });

    }
}
