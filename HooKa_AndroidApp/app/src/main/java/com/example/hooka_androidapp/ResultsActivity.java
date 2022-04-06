package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {

    int userId;
    int sessionId;
    int sessionPin;
    int qnNum;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the message from the intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userId =  Integer.valueOf(intent.getStringExtra("userId"));
        sessionId = Integer.valueOf(intent.getStringExtra("SessionId"));
        qnNum = Integer.valueOf(intent.getStringExtra("qnNumber"));
        sessionPin = Integer.valueOf(intent.getStringExtra("SessionPin"));
    }
}