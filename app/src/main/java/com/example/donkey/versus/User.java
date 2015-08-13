package com.example.donkey.versus;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by donkey on 2015/7/19.
 */
public class User implements Serializable {
    private int user_id;
    private String user_fbid;
    private String user_name;
    private ArrayList<String> user_friendListId=new ArrayList<String>();
    private ArrayList<String> user_friendListName=new ArrayList<String>();
    public void setId(int user_id){
        this.user_id=user_id;
    }
    public void setUserFbid(String user_fbid){
        this.user_fbid=user_fbid;
    }
    public void setUserName(String user_name){
        this.user_name=user_name;
    }
    public void setUserFriendListId(ArrayList<String> user_friendListId){
        this.user_friendListId=user_friendListId;
    }
    public void setUserFriendListName(ArrayList<String> user_friendListName){
        this.user_friendListName=user_friendListName;
    }
    public int getId(){
        return user_id;
    }
    public String getFbid(){
        return user_fbid;
    }
    public String getName(){
        return user_name;
    }
    public ArrayList<String> getUser_friendListId() {
        return user_friendListId;
    }
    public ArrayList<String> getUser_friendListName() {
        return user_friendListName;
    }

}
