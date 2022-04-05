/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ee.hooka;

/**
 *
 * @author zhaoyiwu
 */
public class Session {
    
    private int sessionId;
    private int userId;
    private String sessionName;
    private int sessionPin;
    private int sessionRunningStatus;

    public Session() {
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getSessionPin() {
        return sessionPin;
    }

    public void setSessionPin(int sessionPin) {
        this.sessionPin = sessionPin;
    }
    
    public int getSessionRunningStatus() {
        return sessionRunningStatus;
    }

    public void setSessionRunningStatus(int sessionRunningStatus) {
        this.sessionRunningStatus = sessionRunningStatus;
    }
    
}
