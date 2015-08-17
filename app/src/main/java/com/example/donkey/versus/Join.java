package com.example.donkey.versus;

/**
 * Created by donkey on 2015/8/17.
 */
public class Join {
    private String inviter;
    private String joinRoomId;
    private String joinStart;
    private String joinEnd;
    private String joinName;
    private String joinChallenge;
    public void setInviter(String inviter){
        this.inviter=inviter;
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
    public String getInviter(){
        return  inviter;
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
