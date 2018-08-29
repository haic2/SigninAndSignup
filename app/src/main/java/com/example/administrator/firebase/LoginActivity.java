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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView signup,tvPhSignin;
    private EditText inputEmail, inputPassword;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button btnSignIn, btnResetPassword;
    private ProgressBar progress;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
            startActivity(intent);
        }

        signup = findViewById(R.id.tosignup);
        inputEmail = findViewById(R.id.accountname);
        inputPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        tvPhSignin = findViewById(R.id.gotophsignin);
        progress = findViewById(R.id.progressBar);
        signup.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        tvPhSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //go to sign up page
        if(view == signup) {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }
        if(view == btnSignIn){
            userLogin();
        }
        if(view == tvPhSignin){
            Intent intent = new Intent(LoginActivity.this,PhonesigninActivity.class);
            startActivity(intent);
        }
    }

    private void userLogin(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            //login in successful, go to profile activity
                            finish();
                            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
                            startActivity(intent);
                        }else{
                            //wrong password or email. try again
                            Toast.makeText(LoginActivity.this,"wrong password or email. try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
