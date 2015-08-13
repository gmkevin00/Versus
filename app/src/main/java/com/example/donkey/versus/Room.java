package com.example.donkey.versus;

import java.io.Serializable;

/**
 * Created by donkey on 2015/7/24.
 */
public class Room implements Serializable{
    private static final long serialVersionUID = 1L;
    private int room_id;
    private String room_name;
    private int challenge_id;
    private String challenge_name;
    private int type_id;
    private String type_name;
    private String room_cycle;
    private String room_start;
    private String room_end;
    public int room_star;
    private String room_boss;
    public void setRoomId(int room_id){
        this.room_id=room_id;
    }
    public void setRoomName(String room_name){
        this.room_name=room_name;
    }
    public void setChallengeId(int challenge_id){
        this.challenge_id=challenge_id;
    }
    public void setChallengeName(String challenge_name){
        this.challenge_name=challenge_name;
    }
    public void setTypeId(int type_id){
        this.type_id=type_id;
    }
    public void setTypeName(String type_name){
        this.type_name=type_name;
    }
    public void setRoomCycle(String room_cycle){
        this.room_cycle=room_cycle;
    }
    public void setRoomStart(String room_start){
        this.room_start=room_start;
    }
    public void setRoomEnd(String room_end){
        this.room_end=room_end;
    }
    public void setRoomStar(int room_star){this.room_star=room_star;}
    public int getRoomId(){
        return room_id;
    }
    public String getRoomName(){
        return room_name;
    }
    public int getChallengeId(){
        return challenge_id;
    }
    public String getChallengeName(){
        return challenge_name;
    }
    public int getTypeId(){
        return type_id;
    }
    public String getTypeName(){
        return type_name;
    }
    public String getRoomCycle() {
        return room_cycle;
    }
    public String getRoomStart(){
        return room_start;
    }
    public String getRoomEnd(){
        return room_end;
    }
    public int getRoomStar(){return  room_star;}

    public void setRoomBoss(String room_boss) {
        this.room_boss=room_boss;
    }
    public String getRoomBoss(){
        return room_boss;
    }
}
