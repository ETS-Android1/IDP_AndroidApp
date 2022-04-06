package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooka_androidapp.models.Options;
import com.example.hooka_androidapp.models.Question;
import com.example.hooka_androidapp.models.Responses;
import com.example.hooka_androidapp.models.Session;

public class OptionsActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    private TextView username_TB;
    private TextView qnTxt;
    Options OptionContentA = null;
    Options OptionContentB = null;
    Options OptionContentC = null;
    Options OptionContentD = null;
    Question QuestionContent = null;
    Responses ResponseContentA = null;
    Responses ResponseContentB = null;
    Responses ResponseContentC = null;
    Responses ResponseContentD = null;
    private Button optionA = null;
    private Button optionB = null;
    private Button optionC = null;
    private Button optionD = null;
    int userId;
    int sessionId;
    int sessionPin;
    int qnNum;
    int qnId;
    String username;
    Boolean isActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Get the message from the intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userId =  Integer.valueOf(intent.getStringExtra("userId"));
        sessionId = Integer.valueOf(intent.getStringExtra("SessionId"));
        qnNum = Integer.valueOf(intent.getStringExtra("qnNumber"));
        sessionPin = Integer.valueOf(intent.getStringExtra("SessionPin"));

        username_TB = (TextView) findViewById(R.id.username_TB);
        username_TB.setText(username);

        qnTxt = (TextView) findViewById(R.id.qnTxt);
        qnTxt.setText("Question " + String.valueOf(qnNum));

        content();


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

    public void content() {
        //To get Question data
        try {
            //retrieve question from db
            QuestionContent = Services.questionAvailability(sessionId, qnNum);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        if (QuestionContent.accessible != 0) { //Question not accessible
            linker();
        }
        else{
            qnId = QuestionContent.qnId;
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
                isActive = false;// To remove this ltr

                String temp = "";
                temp += "OptionA not found.";

                Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
            }
            else {
                optionA = (Button) findViewById(R.id.optionA_Btn);
                optionA.setText("A\n\n" + OptionContentA.optionDesc);
                optionB = (Button) findViewById(R.id.optionB_Btn);
                optionB.setText("B\n\n" + OptionContentB.optionDesc);
                optionC = (Button) findViewById(R.id.optionC_Btn);
                optionC.setText("C\n\n" + OptionContentC.optionDesc);
                optionD = (Button) findViewById(R.id.optionD_Btn);
                optionD.setText("D\n\n" + OptionContentD.optionDesc);

            }
        }
        //button A clicks
        optionA.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try {
                    //retrieve question from db
                    ResponseContentA = Services.userResponse(qnId, userId, sessionId, "A");
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                /*if (ResponseContentA == null) {
                    String temp = "";
                    temp += "(Choice A)Please try again";

                    Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    linker();
                }*/
                linker();
            }

        });

        //button B clicks
        optionB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try {
                    //retrieve question from db
                    ResponseContentB = Services.userResponse(qnId, userId, sessionId, "B");
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                linker();
            }

        });

        //button C clicks
        optionC.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try {
                    //retrieve question from db
                    ResponseContentC = Services.userResponse(qnId, userId, sessionId, "C");
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                linker();
            }

        });

        //button D clicks
        optionD.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try {
                    //retrieve question from db
                    ResponseContentD = Services.userResponse(qnId, userId, sessionId, "D");
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                linker();
            }

        });

        if (isActive){
            refresh(1000); //2 seconds
        }
    }

    //To refresh contents every few seconds
    private void refresh(int milliseconds) {
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, milliseconds);
    }

    private void linker() {
        isActive = false;
        qnNum += 1;
        Intent intent = new Intent(OptionsActivity.this,ResultsActivity.class);
        Bundle extras= new Bundle();
        extras.putString("username",username);
        extras.putString("userId", String.valueOf(userId));
        extras.putString("qnNumber", String.valueOf(qnNum));
        extras.putString("SessionId", String.valueOf(sessionId));
        extras.putString("SessionPin", String.valueOf(sessionPin));

        intent.putExtras(extras);
        startActivity(intent);
    }
}