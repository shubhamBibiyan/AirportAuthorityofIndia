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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome_page extends AppCompatActivity  {

    public static TextView sname, semployee_id, sdesignation, sairport, slocation, sSearched_person;
    Button welcomebtn, findbtn;
    DatabaseReference databaseReference;
    String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


        sname = findViewById(R.id.welcome_name);
        semployee_id= findViewById(R.id.welcome_emp_id);
        sdesignation = findViewById(R.id.welcome_desi);
        sairport = findViewById(R.id.welcome_airport);
        slocation = findViewById(R.id.welcome_location);
        sSearched_person =findViewById(R.id.searched_person);
        welcomebtn = findViewById(R.id.next_page);
        findbtn = findViewById(R.id.find_btn_welcome);

                findbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchrd_person = sSearched_person.getText().toString();
                        if(TextUtils.isEmpty(searchrd_person)){
                            Toast.makeText(Welcome_page.this, "Please enter your ID", Toast.LENGTH_SHORT).show();
                        }
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Staff_Data").child(searchrd_person);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.child("fname").getValue().toString();
                                String emp_id = dataSnapshot.child("empid").getValue().toString();
                                String  desi = dataSnapshot.child("designation").getValue().toString();
                                String airport = dataSnapshot.child("airport").getValue().toString();
                                String location = dataSnapshot.child("location").getValue().toString();

                                sname.setText(name);
                                semployee_id.setText(emp_id);
                                sdesignation.setText(desi);
                                sairport.setText(airport);
                                slocation.setText(location);
                                usertype = desi.toLowerCase();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                welcomebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(usertype.equals("admin")){
                            startActivity(new Intent(getApplicationContext(),Management.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(),Technician.class));
                        }

                    }
                });
            }
    }