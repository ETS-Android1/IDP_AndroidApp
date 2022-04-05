package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tv=(TextView)findViewById(R.id.LoginRedirect);

        tv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Create an Intent to start the second activity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                // Start the intended activity
                startActivity(intent);
            }
        });
    }
}