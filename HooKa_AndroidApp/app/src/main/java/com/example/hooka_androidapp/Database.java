package com.example.hooka_androidapp;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Database {

    String res = "";

    private static final String url = "jdbc:mysql://localhost:3306/hooka?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String pass = "xxxx";

    public Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database Connection successsssssssss");

//            String result = "Database Connection Successful\n";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("select distinct fullname from user");
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            while (rs.next()) {
//                result += rs.getString(1).toString() + "\n";
//            }
//            res = result;
        } catch (Exception e) {
            System.out.println("eeeeerrroooorrrr");
            e.printStackTrace();
            res = e.toString();
            System.out.println(res);
        }
        //return res;
        }

}

    //----------------------------------------USERS--------------------------------------------------

    // ---insert
//    public void insertUser(String username, String email, String password) {
//        String sqlStmt = "Insert into " + TABLE_USERS + " (" + USERS_USERNAME + "," + USERS_EMAIL + "," + USERS_PASSWORD + "," + USERS_BALANCE + ") " +
//                "VALUES ('" + username + "','" + email + "','" + password + "','" + 0.00
//                + "')";
//        db.execSQL(sqlStmt);
//    }// insert


