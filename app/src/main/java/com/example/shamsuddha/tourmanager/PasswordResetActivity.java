package com.example.shamsuddha.tourmanager;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {


    EditText resetPasswordEmailET;
    Button resetPasswordButton;
    private  ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        resetPasswordEmailET = findViewById(R.id.resetPasswordEmailET);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        firebaseAuth = firebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordReset();
            }
        });





    }

    private void passwordReset() {
        final ProgressDialog progress = new ProgressDialog(PasswordResetActivity.this);
        progress.setMessage("Verifying...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);

        String email = resetPasswordEmailET.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordResetActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PasswordResetActivity.this, "Please give a registered user email!", Toast.LENGTH_LONG).show();
                        }

                        // progressBar.setVisibility(View.GONE);
                    }
                });
    }


}

