package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hooka_androidapp.models.Question;
import com.example.hooka_androidapp.models.Responses;

public class ResultsActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    private TextView usernameResults_TB;
    private TextView ansValidation_TB;
    private TextView pointsTxt_TB;
    Question QuestionContent = null;
    Question QuestionContentNext = null;
    Responses ResponseContent = null;
    int userId;
    int sessionId;
    int sessionPin;
    int qnNum;
    int qnId;
    int ttlQns;
    String username;
    Boolean isActive = true;

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
        ttlQns = Integer.valueOf(intent.getStringExtra("ttlQns"));

        usernameResults_TB = (TextView) findViewById(R.id.usernameResults_TB);
        usernameResults_TB.setText(username);

        ansValidation_TB = (TextView) findViewById(R.id.AnswerValidationTxt);
        pointsTxt_TB = (TextView) findViewById(R.id.pointsTxt);

        content();
    }

    public void content() {
        try {
            //retrieve question from db
            QuestionContent = Services.questionAvailability(sessionId, qnNum);
            if(qnNum != ttlQns+1){
                QuestionContentNext = Services.questionAvailability(sessionId, qnNum+1);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        if(QuestionContent.accessible == 1){
            qnId = QuestionContent.qnId;
            try {
                //retrieve question from db
                ResponseContent = Services.responseData(qnId, userId, sessionId);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            if(ResponseContent.points == 10) {
                ansValidation_TB.setText("Correct Answer!");
                ansValidation_TB.setBackgroundColor(Color.GREEN);
            }
            else{
                ansValidation_TB.setText("Wrong Answer :(");
                ansValidation_TB.setBackgroundColor(Color.RED);
            }
            pointsTxt_TB.setText("You earn " + ResponseContent.points + " points");
        }
        if(qnNum != ttlQns+1){
            if (QuestionContentNext.accessible == 0){
                isActive = false;

                Intent intent = new Intent(ResultsActivity.this,OptionsActivity.class);
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
        if(qnNum == ttlQns+1) {
            isActive = false;

            Intent intent = new Intent(ResultsActivity.this,MainActivity.class);
            Bundle extras= new Bundle();
            extras.putString("username",username);
            extras.putString("userId", String.valueOf(userId));

            intent.putExtras(extras);
            startActivity(intent);
        }



        if (isActive){
            refresh(1000); //1 seconds
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
}