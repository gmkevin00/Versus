package com.example.donkey.versus;

/**
 * Created by donkey on 2015/9/24.
 */
public class achievement {
    private String achievementName;
    private int flag=0;
    public void setAchievementName(String achievementName)
    {
        this.achievementName=achievementName;
    }
    public void setFlag(int flag)
    {
        this.flag=flag;
    }
    public String getAchievementName()
    {
        return achievementName;
    }
    public int getFlag(){
        return flag;
    }
}
