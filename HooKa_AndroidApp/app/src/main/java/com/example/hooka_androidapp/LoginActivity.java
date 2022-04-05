package com.example.hooka_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hooka_androidapp.models.User;

public class LoginActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    TextView fullnameTB = null;
    TextView passwordTB = null;
    Button loginBtn = null;
    User userRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fullnameTB = findViewById(R.id.usernameTB);
        passwordTB = findViewById(R.id.passwordTB);
        loginBtn = (Button) findViewById(R.id.loginBTN);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String fullname = fullnameTB.getText().toString();
                String password = passwordTB.getText().toString();

                try {
                    //retrieve user from db
                    userRetrieved = Services.login(fullname, password);

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                } finally {
//                        if (dbAdaptor != null)
//                            dbAdaptor.close();

                    //search for userId
//                        if(createSuccessful){
//
//                        }

                    //save user details in intent
                    SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = user.edit();

                    //editor.putInt("userId", id);
                    editor.putString("fullname", fullname);
                    editor.putInt("joinedSession", -1);
                    editor.commit();

                }
                if (userRetrieved == null) {
                    passwordTB.setText("");
                    fullnameTB.setText("");

                    String temp = "";
                    temp += "User not found."+ "\n";
                    temp += "Please try again";

                    Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                    Bundle extras= new Bundle();
                    extras.putString("username",fullnameTB.getText().toString());
                    intent.putExtras(extras);

                    startActivity(intent);
                }
            }
        });

        TextView tv=(TextView)findViewById(R.id.RegisterRedirect);

        tv.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                // Create an Intent to start the second activity
                Intent intent = new Intent(LoginActivity.this, Register.class);

                // Start the intended activity
                startActivity(intent);
            }
        });
    }
}