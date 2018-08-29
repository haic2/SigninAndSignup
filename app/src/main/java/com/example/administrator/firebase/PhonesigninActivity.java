package com.example.administrator.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PhonesigninActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private EditText phonenum;
    private Button btnContinue;
    private TextView tvSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonesignup);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CounrtyData.countryNames));
        phonenum = findViewById(R.id.phonenumber);
        btnContinue = findViewById(R.id.btnContinue);
        tvSignin = findViewById(R.id.gotologin);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnContinue){
            String code = CounrtyData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String number = phonenum.getText().toString().trim();
            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                finish();
                Intent intent = new Intent(this, profileActivity.class);
                startActivity(intent);
            }
            if(number.isEmpty() || number.length() < 10){
                phonenum.setError("Valid number is required");
                phonenum.requestFocus();
                return;
            }

            String phoneNumber = '+' + code + number;
            Intent intent = new Intent(PhonesigninActivity.this,PhoneVerifyActivity.class);
            intent.putExtra("phonenumber", phoneNumber);
            startActivity(intent);
        }

        if(view == tvSignin){
            Intent intent = new Intent(PhonesigninActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
