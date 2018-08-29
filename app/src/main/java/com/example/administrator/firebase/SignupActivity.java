package com.example.administrator.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText inputEmail, inputPassword;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button btnSignUp, btnResetPassword;
    private  TextView tvSignin;
    private ProgressBar progress;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
            startActivity(intent);
        }

        btnSignUp = findViewById(R.id.signup);
        tvSignin = findViewById(R.id.gotologin);
        inputEmail = findViewById(R.id.accountname);
        inputPassword = findViewById(R.id.password);
        progress = findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(this);
        tvSignin.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if(view == btnSignUp) {
            registerNewUsers();
        }
        if(view == tvSignin){
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }
    public void registerNewUsers(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password is too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            finish();
                            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
                            startActivity(intent);
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "This email address is already registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }



}
