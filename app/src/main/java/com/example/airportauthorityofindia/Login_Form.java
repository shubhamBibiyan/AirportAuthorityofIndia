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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Form extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    ProgressBar mLog_Load;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);
        getSupportActionBar().setTitle("Login Form");

        mEmail      = findViewById(R.id.LEmail);
        mPassword   = findViewById(R.id.LPassword);
        mLoginBtn   = findViewById(R.id.LLoginBtn);
        mLog_Load   = findViewById(R.id.Log_Load);
        fAuth       = FirebaseAuth.getInstance();

        mLog_Load.setVisibility(View.GONE);

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login_Form.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login_Form.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6 ){
                    Toast.makeText(Login_Form.this, "Just a reminder that your password has more than 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login_Form.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mLog_Load.setVisibility(View.VISIBLE);
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), Welcome_page.class));
                        }else{
                            Toast.makeText(Login_Form.this, "Email/Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), Signup_Form.class));
    }
}
