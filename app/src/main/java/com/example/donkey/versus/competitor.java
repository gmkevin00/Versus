package com.example.donkey.versus;

/**
 * Created by donkey on 2015/8/20.
 */
public class Competitor extends User {
    private int totalCount=0;
    public void setCount(int totalCount)
    {
        this.totalCount=totalCount;
    }
    public void addTotalCount()
    {
        this.totalCount+=1;
    }
    public int getTotalCount()
    {
        return totalCount;
    }
}
