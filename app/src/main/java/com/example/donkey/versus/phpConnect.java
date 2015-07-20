package com.example.donkey.versus;

/**
 * Created by donkey on 2015/7/1.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class phpConnect {
    ProgressDialog progressdialog;
    GetUserCallback userCallback;
    private URL url;
    private HttpURLConnection conn;
    public phpConnect(Context showContext,String text){
        progressdialog=new ProgressDialog(showContext);
        progressdialog.setCancelable(false);
        progressdialog.setTitle("處理中");
        progressdialog.setMessage(text);
    }
    public void setUrl(String urlString){
        try {
            url=new URL(urlString);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void execute(GetUserCallback userCallback) {
        progressdialog.show();
        new phpDataExchange(userCallback).execute();
    }
    public class phpDataExchange extends AsyncTask<Void, Void, JSONArray>{
        GetUserCallback userCallback;
        JSONArray jsonarray=new JSONArray();
        public phpDataExchange(GetUserCallback userCallback){
            this.userCallback=userCallback;
        }
        @Override
        protected JSONArray doInBackground(Void[] arg0) {
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                reader.close();
                try {
                    jsonarray=new JSONArray(sb.toString());
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
        protected void onPostExecute(JSONArray result){
            super.onPostExecute(result);
           progressdialog.dismiss();
            userCallback.done(jsonarray);
        }
    }
}
