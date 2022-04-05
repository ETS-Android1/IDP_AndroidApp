package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /** Callback when the user selects the Send button */
    public void toRegisterBtn(View view) {
        // Create an Intent to start the second activity
        Intent intent = new Intent(this, RegisterActivity.class);

        // Start the intended activity
        startActivity(intent);
    }
}