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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Signup_Form extends AppCompatActivity {

    EditText mEmp_Email, mEmp_Pass, mEmp_ConPass;
    Button mRegister_Btn;
    ProgressBar mLoad;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
        getSupportActionBar().setTitle("Signup Form");


        mEmp_Email = findViewById(R.id.Emp_Email);
        mEmp_Pass = findViewById(R.id.Emp_Pass);
        mEmp_ConPass = findViewById(R.id.Emp_ConPass);
        mRegister_Btn = findViewById(R.id.Register_Btn);
        mLoad = findViewById(R.id.Reg_Load);
        fAuth = FirebaseAuth.getInstance();



        mRegister_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmp_Email.getText().toString().trim();
                final String password = mEmp_Pass.getText().toString().trim();
                String conpassword = mEmp_ConPass.getText().toString().trim();



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Signup_Form.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup_Form.this, "Choose a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(conpassword)) {
                    Toast.makeText(Signup_Form.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Signup_Form.this, "Password is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.equals(conpassword)){
                    fAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(), Staff_Data.class));
                                        Toast.makeText(Signup_Form.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Signup_Form.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Signup_Form.this, "Your password didn't match.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

