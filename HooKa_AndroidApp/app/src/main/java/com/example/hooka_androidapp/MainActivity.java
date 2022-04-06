package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.hooka_androidapp.models.User;

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    private TextView WelcomTxt;
    TextView sessionPinTxt = null;
    Button joinSessionBtn = null;
    User UserContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Database database = new Database();
//        database.execute();

        // Get the message from the intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int userId =  Integer.valueOf(intent.getStringExtra("userId"));

        WelcomTxt = (TextView) findViewById(R.id.WelcomeTxt);
        WelcomTxt.setText("Welcome " + username);

        sessionPinTxt = findViewById(R.id.join_sessionPin);
        joinSessionBtn = (Button) findViewById(R.id.join_enterSession);


        joinSessionBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Integer sessionPin = Integer.valueOf(sessionPinTxt.getText().toString());
                try {
                    //retrieve user from db
                    UserContent = Services.joinSession(userId, sessionPin);

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                if (UserContent == null) {
                    sessionPinTxt.setText("");

                    String temp = "";
                    temp += "Session not found."+ "\n";
                    temp += "Please try again";

                    Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,LoadingActivity.class);

                    Bundle extras= new Bundle();
                    extras.putString("username",username);
                    extras.putString("userId", String.valueOf(UserContent.userId));
                    extras.putString("sessionPin",sessionPinTxt.getText().toString());
                    extras.putString("previousPage", "SessionJoin");
                    extras.putString("qnNum", "1");
                    intent.putExtras(extras);

                    startActivity(intent);
                }
            }
        });
    }
}
