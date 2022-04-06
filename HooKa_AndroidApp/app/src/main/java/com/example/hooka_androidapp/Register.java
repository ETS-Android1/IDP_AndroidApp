package com.example.hooka_androidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hooka_androidapp.models.User;

public class Register extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    TextView fullnameTB = null;
    TextView mobileTB = null;
    TextView passwordTB = null;
    TextView confirmPasswordTB = null;
    Button registerBtn = null;
    User userAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameTB = findViewById(R.id.reg_usernameTB);
        mobileTB = findViewById(R.id.reg_mobileTB);
        passwordTB = findViewById(R.id.reg_passwordTB);
        confirmPasswordTB = findViewById(R.id.reg_confirmPasswordTB);
        registerBtn = (Button) findViewById(R.id.reg_registerBTN);

        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){

                if(passwordTB.getText().toString().equals(confirmPasswordTB.getText().toString()))
                {
                    String fullname = fullnameTB.getText().toString();
                    String mobile = mobileTB.getText().toString();
                    String password = passwordTB.getText().toString();

                    try {
                        //insert user into db
                        userAccount = Services.createUser("Student", fullname, mobile, password);

                        if (userAccount == null) {
                            String temp = "";
                            temp += "Confirm Password is wrong."+ "\n";
                            temp += "Please try again";

                            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                            return;
                        }

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
                        editor.putString("mobile", mobile);
                        editor.putInt("joinedSession", -1);
                        editor.commit();

                    }

//                    saveAsPreferences();

                    Intent intent = new Intent(Register.this,MainActivity.class);

                    Bundle extras= new Bundle();
                    extras.putString("username",fullnameTB.getText().toString());
                    extras.putString("userId", String.valueOf(userAccount.userId));
                    intent.putExtras(extras);

                    startActivity(intent);
                }
                else
                {
                    passwordTB.setText("");
                    confirmPasswordTB.setText("");

                    String temp = "";
                    temp += "Confirm Password is wrong."+ "\n";
                    temp += "Please try again";

                    Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
                }


            }
        });

        TextView tv=(TextView)findViewById(R.id.LoginRedirect);

        tv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Create an Intent to start the second activity
                Intent intent = new Intent(Register.this, LoginActivity.class);

                // Start the intended activity
                startActivity(intent);
            }
        });

    }

//    public void saveAsPreferences()
//    {
//        DBAdapter dbAdaptor = new DBAdapter(getApplicationContext());
//        Cursor cursor = null;
//        dbAdaptor.open();
//        cursor = dbAdaptor.getUserId();
//        cursor.moveToFirst();
//        int id = cursor.getInt(0);
//
//        String username = usernameTB.getText().toString();
//        String email = emailTB.getText().toString();
//
//        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
//        SharedPreferences.Editor editor = user.edit();
//
//        editor.putInt("userId", id);
//        editor.putString("username", username);
//        editor.putString("email", email);
//        editor.putFloat("balance", 0);
//
//
//        editor.commit();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart...");
    }
}
