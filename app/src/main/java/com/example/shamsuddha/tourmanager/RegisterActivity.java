package com.example.shamsuddha.tourmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText registerEmailAddressET, registerPasswordET;
    Button registerButton;
    TextView loginTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // VIEWS
        registerEmailAddressET = findViewById(R.id.registerEmailAddressET);
        registerPasswordET = findViewById(R.id.registerPasswordET);
        loginTV = findViewById(R.id.loginTV);
        registerButton = findViewById(R.id.registerButton);


        //CHECK THE USER IS LOGGED IN OR NOT... IF LOGGED IN THEN TRANSFER TO DASHBOARD..
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(i);
        }


        // LOGIN TEXT ONCLICK
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(login);
            }
        });


        // REGISTER BUTTON ONCLICK
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }



    // REGISTER FUNCTIONALITY
    private void registerUser() {
        String email = registerEmailAddressET.getText().toString();
        String password = registerPasswordET.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // user is successfully registered
                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Could not register, try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }





}
