package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView WelcomTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database database = new Database();
        database.execute();

        // Get the message from the intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        WelcomTxt = (TextView) findViewById(R.id.WelcomeTxt);
        WelcomTxt.setText("Welcome " + username);
    }

    public void enterSessionBttn(View view) {
        // Create an Intent to start the second activity
        Intent intent = new Intent(MainActivity.this, LoadingActivity.class);

        // Start the intended activity
        startActivity(intent);
    }


}
