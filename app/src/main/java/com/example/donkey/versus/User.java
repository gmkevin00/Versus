package com.example.donkey.versus;
import java.util.ArrayList;

/**
 * Created by donkey on 2015/7/19.
 */
public class User {
    private int user_id;
    private String user_fbid;
    private String user_name;
    private ArrayList<String> user_friendList=new ArrayList<String>();
    public void setId(int user_id){
        this.user_id=user_id;
    }
    public void setUserFbid(String user_fbid){
        this.user_fbid=user_fbid;
    }
    public void setUserName(String user_name){
        this.user_name=user_name;
    }
    public void setUserFriendList(ArrayList<String> user_friendList){
        this.user_friendList=user_friendList;
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
    public ArrayList<String> getUserFriendList() {
        return user_friendList;
    }

}
