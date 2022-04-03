package com.example.hooka_androidapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.net.ssl.HttpsURLConnection;

public class Database extends AsyncTask<String, String, String> {

    String myUrl = "http://localhost:3000/users";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog to show the user what is happening
    }

        @Override
        protected String doInBackground(String... params) {
// Fetch data from the API in the background.

            String result = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(myUrl);
                    //open a URL coonnection

                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();

                    while (data != -1) {
                        result += (char) data;
                        data = isw.read();

                    }

                    // return the data to onPostExecute method
                    return result;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray1 = jsonObject.getJSONArray("users");

                JSONObject jsonObject1 =jsonArray1.getJSONObject(0);
                String id = jsonObject1.getString("id");
                String name = jsonObject1.getString("name");
                String my_users = "User ID: "+id+"\n"+"Name: "+name;

                //Show the Textview after fetching data


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

}


