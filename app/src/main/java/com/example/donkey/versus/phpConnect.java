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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class phpConnect {
    ProgressDialog progressdialog;
    GetUserCallback userCallback;

    private URL url;
    private HttpURLConnection conn;

    private ArrayList<String> sendVar=new ArrayList<String>();
    private ArrayList<String> sendText=new ArrayList<String>();
    private boolean progresFlag=true;

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

    public void addSendData(String sendVal,String sendText){
        this.sendVar.add(sendVal);
        this.sendText.add(sendText);
    }

    public String getSendData(){
        String sendString="";
        for(int i=0;i<sendVar.size();i++)
        {
            try
            {
                sendString+="&";
                sendString+=""+URLEncoder.encode(sendVar.get(i), "UTF-8");
                sendString+="=";
                sendString+=""+URLEncoder.encode(sendText.get(i), "UTF-8");
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return  sendString;
    }

    public void setProgresFlag(Boolean flag)
    {
        this.progresFlag=flag;
    }

    public void execute(GetUserCallback userCallback) {
        if(progresFlag)
        {
            progressdialog.show();
        }
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
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                if(getSendData()!=null)
                {
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getSendData());
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                //Log.d("DebugLog", sb.toString());
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
            if(progresFlag)
            {
                progressdialog.dismiss();
            }
            userCallback.done(jsonarray);
        }
    }
}
