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

public class MainActivity extends AppCompatActivity {

    EditText loginEmailAddressET, loginPasswordET;
    TextView mForgetPasswordText, registerTV;
    FirebaseAuth firebaseAuth;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VIEWS
        mForgetPasswordText = findViewById(R.id.forgetPasswordTV);
        loginEmailAddressET = findViewById(R.id.loginEmailAddressET);
        loginPasswordET = findViewById(R.id.loginPasswordET);
        registerTV = findViewById(R.id.registerTV);
        loginButton = findViewById(R.id.loginButton);



        // CHECK THE USER IS LOGGED IN OR NOT? IF LOGGED IN REDIRECT TO DASHBOARD..
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(i);
        }


        // LOGIN BUTTON ONCLICK
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        // FORGET PASSWORD ACTIVITY
        mForgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetPassword = new Intent(MainActivity.this, PasswordResetActivity.class);
                startActivity(forgetPassword);
            }
        });

        // REGISTER ACTIVITY
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });


    }



    // USER LOGIN FUNCTION
    private void userLogin() {
        String emailAddress = loginEmailAddressET.getText().toString();
        String password = loginPasswordET.getText().toString();
        if (TextUtils.isEmpty(emailAddress)){
            Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            finish();
                            Intent i = new Intent(MainActivity.this,DashboardActivity.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please enter correct email and password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
