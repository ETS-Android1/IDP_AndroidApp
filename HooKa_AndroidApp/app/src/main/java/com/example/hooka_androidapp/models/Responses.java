package com.example.hooka_androidapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Responses {
    public int responseId;
    public int userId;
    public int qnId;
    public int sessionId;
    public String choice;
    public int points;
}
