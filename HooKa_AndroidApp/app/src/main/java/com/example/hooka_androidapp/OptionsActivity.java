package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {
    private TextView txtTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        txtTimer = (TextView)  findViewById(R.id.txtTimer);
        // Count down from 30 sec. onTick() every second. Values in milliseconds
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisRemaining) {
                String stringTime = getString(R.string.TimeLeft);
                txtTimer.setText(stringTime + millisRemaining / 1000);
            }
            public void onFinish() {
                String stringTime = getString(R.string.TimesUp);
                txtTimer.setText(stringTime);
            }
        }.start();
    }
}