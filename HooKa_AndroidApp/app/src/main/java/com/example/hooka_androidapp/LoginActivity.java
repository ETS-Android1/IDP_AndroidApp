package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv=(TextView)findViewById(R.id.RegisterRedirect);

        tv.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                // Create an Intent to start the second activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                // Start the intended activity
                startActivity(intent);
            }
        });
    }

    public  void loginBttn(View view) {
        // Create an Intent to start the second activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        // Start the intended activity
        startActivity(intent);
    }
}