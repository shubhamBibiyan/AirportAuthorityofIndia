package com.example.airportauthorityofindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Staff_Data extends AppCompatActivity {

    EditText mEmp_Id, mEmp_Name, mEmp_Desi, mEmp_WAirport, mEmp_WLoc, mAddress;
    RadioButton mRadioMale, mRadioFemale;
    ProgressBar mUpdate_Load;
    Button mUpdateBtn;
    String gender = "";
    private FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Staff information;
    long maxID = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__data);

        mEmp_Id         = findViewById(R.id.Emp_Id);
        mEmp_Name       = findViewById(R.id.Emp_Name);
        mEmp_Desi       = findViewById(R.id.Emp_Desi);
        mAddress        = findViewById(R.id.Address);
        mRadioMale      = findViewById(R.id.RMale);
        mRadioFemale    = findViewById(R.id.RFemale);
        mEmp_WAirport   = findViewById(R.id.Emp_WAirport);
        mEmp_WLoc       = findViewById(R.id.Emp_WLoc);
        mUpdateBtn      = findViewById(R.id.UpdateBtn);
        mUpdate_Load    = findViewById(R.id.Update_Load);
        fAuth           = FirebaseAuth.getInstance();
        information     = new Staff();

        //Steps to store data into Firebase database
        databaseReference = firebaseDatabase.getInstance().getReference().child("Staff_Data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxID = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String empid = mEmp_Id.getText().toString().trim();
                final String fname = mEmp_Name.getText().toString().trim();
                String address     = mAddress.getText().toString().trim();
                String designation = mEmp_Desi.getText().toString().trim();
                String airport     = mEmp_WAirport.getText().toString().trim();
                String location    = mEmp_WLoc.getText().toString().trim();

                if (mRadioMale.isChecked()) {
                    gender = "Male";
                }
                if (mRadioMale.isChecked()) {
                    gender = "Female";
                }

                if (TextUtils.isEmpty(empid)) {
                    Toast.makeText(Staff_Data.this, "Please Enter your ID number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(Staff_Data.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(Staff_Data.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(designation)) {
                    Toast.makeText(Staff_Data.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(airport)) {
                    Toast.makeText(Staff_Data.this, "Please Enter Airport", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(Staff_Data.this, "Please Enter Location", Toast.LENGTH_SHORT).show();
                    return;
                }


                information.setEmpid(empid);
                information.setFname(fname);
                information.setGender(gender);
                information.setAddress(address);
                information.setDesignation(designation);
                information.setAirport(airport);
                information.setLocation(location);

                databaseReference.child(String.valueOf(maxID+1)).setValue(information);

                //Original Code
                // databaseReference.push().setValue(information);
                Toast.makeText(Staff_Data.this, "Data is successfully added.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login_Form.class));
            }
        });
        }
}
