package com.example.hooka_androidapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public int userId;
    public String userType;
    public String fullname;
    public String mobile;
    public String password;

    public User() {}

    public User(String userType, String fullname, String mobile, String password) {
        this.userType = userType;
        this.fullname = fullname;
        this.mobile = mobile;
        this.password = password;
    }
}

//[{"userId":1,"userType":"Instructor","fullname":"admin","mobile":"98765432","password":"admin"},{"userId":3,"userType":"Instructor","fullname":"test","mobile":"123","password":"abc"},{"userId":4,"userType":"Student","fullname":"test","mobile":"123","password":"abc"},{"userId":5,"userType":"Student","fullname":"test1","mobile":"123","password":"abc"}]