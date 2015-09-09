package com.example.donkey.versus;

/**
 * Created by donkey on 2015/9/8.
 */

public class chat {
    private String content;
    private String userid;
    private String userName;
    private String time;


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private void setUsername(String userName)
    {
        this.userName=userName;
    }

    public String getContent() {
        return content;
    }

    public String getUserid() {
        return userid;
    }
    public String getUserName()
    {
        return userName;
    }
}
