package com.example.uday_vig.mapmyindia;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    TextView signupToLogin;

    private EditText etUserEmail, etUserPassword;
    private FloatingActionButton btnSignUp;

    private String userPassword, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignUp = findViewById(R.id.btnSignUp);
        etUserEmail = findViewById(R.id.emailEditText);

        etUserPassword = findViewById(R.id.userPasswordEditText);
        signupToLogin = findViewById(R.id.signupToLoginTextView);

        mFirebaseAuth=FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = etUserEmail.getText().toString();
                userPassword = etUserPassword.getText().toString();

                final ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, "","Creating user...", true);
                mFirebaseAuth.createUserWithEmailAndPassword(userEmail.trim(), userPassword.trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        startActivity(new Intent(SignupActivity.this, LocationActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Account Creation Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signupToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
