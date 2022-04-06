package com.example.hooka_androidapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Question {
    public int qnId;
    public int sessionId;
    public int qnNumber;
    public String qnDesc;
    public String answer;
    public int accessible;
}
