package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooka_androidapp.models.Options;
import com.example.hooka_androidapp.models.Question;
import com.example.hooka_androidapp.models.Session;

public class OptionsActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    private TextView txtTimer;
    private  TextView qnTxt;
    Options OptionContentA = null;
    Options OptionContentB = null;
    Options OptionContentC = null;
    Options OptionContentD = null;
    Question QuestionContent = null;
    Button optionA = null;
    Button optionB = null;
    Button optionC = null;
    Button optionD = null;
    int userId;
    int sessionId;
    int sessionPin;
    int qnNum;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Get the message from the intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        userId =  Integer.valueOf(intent.getStringExtra("userId"));
        sessionId = Integer.valueOf(intent.getStringExtra("sessionId"));
        qnNum = Integer.valueOf(intent.getStringExtra("qnNum"));
        sessionPin = Integer.valueOf(intent.getStringExtra("sessionPin"));

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtTimer.setText(username);

        qnTxt = (TextView) findViewById(R.id.qnTxt);
        qnTxt.setText(qnNum);


/*
        //countdown timer
        txtTimer = (TextView) findViewById(R.id.txtTimer);
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
 */

    }

    public void content(int sessionId, int qnNum) {
        //To get Question data
        try {
            //retrieve question from db
            QuestionContent = Services.questionAvailability(sessionId, qnNum);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        if (QuestionContent == null) {
            String temp = "";
            temp += "Qn not found.";

            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
        }
        else{
            int qnId = QuestionContent.qnId;
            try{
                //retrieve options from db
                OptionContentA = Services.optionsRetrieval(qnId, "A");
                OptionContentB = Services.optionsRetrieval(qnId, "B");
                OptionContentC = Services.optionsRetrieval(qnId, "C");
                OptionContentD = Services.optionsRetrieval(qnId, "D");
            }
            catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            if (OptionContentA == null) {
                String temp = "";
                temp += "OptionA not found.";

                Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
            }
            else {
                optionA.setText("A\n\n" + OptionContentA.optionDescription);
                optionB.setText("B\n\n" + OptionContentB.optionDescription);
                optionC.setText("C\n\n" + OptionContentC.optionDescription);
                optionD.setText("D\n\n" + OptionContentD.optionDescription);
            }
        }

    }
}