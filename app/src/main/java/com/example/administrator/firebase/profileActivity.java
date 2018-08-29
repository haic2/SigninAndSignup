package com.example.administrator.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        btnLogout = findViewById(R.id.btnlogout);

        btnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == btnLogout){
            auth.signOut();
            finish();
            Intent intent = new Intent(profileActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
