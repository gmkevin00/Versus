package com.example.donkey.versus;

import java.io.Serializable;

/**
 * Created by donkey on 2015/9/6.
 */
public class personalProcess implements Serializable {
    private int check;
    private String time;
    public void setCheck(int check)
    {
        this.check=check;
    }
    public void setTime(String time)
    {
        this.time=time;
    }
    public int getCheck()
    {
        return check;
    }
    public String getTime()
    {
        return time;
    }
}
