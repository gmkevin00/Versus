package com.example.donkey.versus;

import java.io.Serializable;

/**
 * Created by donkey on 2015/8/17.
 */
public class Join implements Serializable {
    private String inviterId;
    private String inviterName;
    private String joinRoomId;
    private String joinStart;
    private String joinEnd;
    private String joinName;
    private String joinChallenge;
    public void setInviterId(String inviterId){
        this.inviterId=inviterId;
    }
    public void setInviterName(String inviterName){
        this.inviterName=inviterName;
    }
    public void setJoinRoomId(String joinRoomId){
        this.joinRoomId=joinRoomId;
    }
    public void setJoinStart(String joinStart){
        this.joinStart=joinStart;
    }
    public void setJoinEnd(String joinEnd){
        this.joinEnd=joinEnd;
    }
    public void setJoinName(String joinName){
        this.joinName=joinName;
    }
    public void setJoinChallenge(String joinChallenge){
        this.joinChallenge=joinChallenge;
    }
    public String getInviterId(){
        return  inviterId;
    }
    public String getInviterName(){
        return  inviterName;
    }
    public String getJoinRoomId(){
        return joinRoomId;
    }
    public String getJoinStart(){
        return joinStart;
    }
    public String getJoinEnd(){
        return joinEnd;
    }
    public String getJoinName(){
        return joinName;
    }
    public String getJoinChallenge(){
        return joinChallenge;
    }
}
