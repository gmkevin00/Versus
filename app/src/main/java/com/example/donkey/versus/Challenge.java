package com.example.donkey.versus;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by donkey on 2015/7/31.
 */
public class Challenge implements Serializable{
    private ArrayList typeList=new ArrayList();
    private int typeId;
    private String typeName;
    public void setTypeId(int typeId){
        this.typeId=typeId;
    }
    public void setTypeName(String typeName){
        this.typeName=typeName;
    }
    public void addList(String type){
        typeList.add(type);
    }
    public String getTypeName(){
        return typeName;
    }
    public int getTypeId(){
        return typeId;
    }
    public ArrayList getTypeList(){
        return typeList;
    }
}
