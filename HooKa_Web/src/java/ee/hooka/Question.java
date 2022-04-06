/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ee.hooka;

import java.util.ArrayList;

/**
 *
 * @author zhaoyiwu
 */
public class Question {
    
    private int qnId;
    private int sessionId;
    private int qnNumber;
    private String qnDesc;
    private String answer;
    private boolean accessible;
    private ArrayList<Option> options;

    public Question() {
    }

    public int getQnId() {
        return qnId;
    }

    public void setQnId(int qnId) {
        this.qnId = qnId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getQnNumber() {
        return qnNumber;
    }

    public void setQnNumber(int qnNumber) {
        this.qnNumber = qnNumber;
    }
    
    public String getQnDesc() {
        return qnDesc;
    }

    public void setQnDesc(String qnDesc) {
        this.qnDesc = qnDesc;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

}
