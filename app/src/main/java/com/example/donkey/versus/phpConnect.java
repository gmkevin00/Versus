package com.example.donkey.versus;

/**
 * Created by donkey on 2015/7/14.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by donkey on 2015/7/1.
 */

public class phpConnect extends AsyncTask {
    private URL url;
    private HttpURLConnection conn;
    private int jsonCount=0;
    private boolean finish=false;
    private JSONArray jsonarray;
    public void setFinish(boolean finish){
        this.finish=finish;
    }
    public void setUrl(String urlString){
        try {
            url=new URL(urlString);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setJSON(JSONArray jsonarray){
        this.jsonarray=jsonarray;
    }
    public void setCount(int jsonCount){
        this.jsonCount=jsonCount;
    }
    public JSONArray getJSON(){
        while(finish==false){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(finish==true){
                break;
            }
        }
        return jsonarray;
    }
    public int getCount(){
        while(finish==false){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(finish==true){
                break;
            }
        }
        return jsonCount;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            int count=0;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                count++;
                Log.i("CountCount","XD");
            }
            reader.close();
            try {
                JSONArray j=new JSONArray(sb.toString());
                setJSON(j);
                setCount(count);
                setFinish(true);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("getJSONFromUrl Exception Error :"+e);
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
