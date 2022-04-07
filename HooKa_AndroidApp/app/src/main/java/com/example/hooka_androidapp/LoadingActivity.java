package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.hooka_androidapp.models.Question;
import com.example.hooka_androidapp.models.Session;

public class LoadingActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    private TextView loadingTxt;
    private TextView usernameTB;
    Session SessionContent = null;
    Question QuestionContent = null;
    Question QuestionContentCurrent = null;

    int userId;
    int sessionPin;
    int sessionId;
    int qnNum;
    String previousPage;
    String username;
    boolean isActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingTxt = (TextView) findViewById(R.id.loadingTxt);

        // Get the message from the intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userId =  Integer.valueOf(intent.getStringExtra("userId"));
        sessionPin = Integer.valueOf(intent.getStringExtra("sessionPin"));
        previousPage = intent.getStringExtra("previousPage");
        qnNum = Integer.valueOf(intent.getStringExtra("qnNum")) + 1;

        usernameTB = (TextView) findViewById(R.id.usernameTB);
        usernameTB.setText(username);

        content();
    }

    public void content() {
        //to get session ID
        try {
            //retrieve session from db
            SessionContent = Services.sessionAvailability(sessionPin);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        if (SessionContent == null) { //Session has ended
            String temp = "";
            temp += "Session has ended."+ "\n";
            temp += "Thank you for using HooKa";

            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
            Bundle extras= new Bundle();
            extras.putString("username",username);
            extras.putString("userId", String.valueOf(userId));

            intent.putExtras(extras);
            startActivity(intent);
        }
        else {
            sessionId = SessionContent.sessionId;

            try {
                //retrieve question from db
                QuestionContent = Services.questionAvailability(sessionId, qnNum);
                QuestionContentCurrent = Services.questionAvailability(sessionId, qnNum-1);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            if (QuestionContent.accessible == 0) { //Question is Accessible
                isActive = false;
                Intent intent = new Intent(LoadingActivity.this,OptionsActivity.class);
                Bundle extras= new Bundle();
                extras.putString("username",username);
                extras.putString("userId", String.valueOf(userId));
                extras.putString("qnNumber", String.valueOf(qnNum));
                extras.putString("SessionId", String.valueOf(sessionId));
                extras.putString("SessionPin", String.valueOf(sessionPin));

                intent.putExtras(extras);
                startActivity(intent);
            }
            else if (QuestionContentCurrent.accessible == 1) { //Show results of qn
                isActive = false;
                Intent intent = new Intent(LoadingActivity.this,ResultsActivity.class);
                Bundle extras= new Bundle();
                extras.putString("username",username);
                extras.putString("userId", String.valueOf(userId));
                extras.putString("qnNumber", String.valueOf(qnNum-1));
                extras.putString("SessionId", String.valueOf(sessionId));
                extras.putString("SessionPin", String.valueOf(sessionPin));

                intent.putExtras(extras);
                startActivity(intent);
            }
            else { //Next Question still not accessible
                if (previousPage.equals("SessionJoin")) {
                    loadingTxt.setText("Entered session " + sessionPin + "!\nWaiting for more students to join..");
                }
                else {
                    loadingTxt.setText("You're fast! \nWaiting for question results :D");
                }
            }
        }
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
}