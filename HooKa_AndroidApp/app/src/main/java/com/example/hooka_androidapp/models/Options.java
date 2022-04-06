package com.example.hooka_androidapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Options {
    public int optionsId;
    public int qnId;
    public int qnNumber;
    public String optionLetter;
    public String optionDescription;
}
