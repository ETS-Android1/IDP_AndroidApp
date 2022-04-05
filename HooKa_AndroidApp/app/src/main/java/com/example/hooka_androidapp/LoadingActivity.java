package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class LoadingActivity extends AppCompatActivity {

    private TextView loadingTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingTxt = (TextView) findViewById(R.id.loadingTxt);


    }
}