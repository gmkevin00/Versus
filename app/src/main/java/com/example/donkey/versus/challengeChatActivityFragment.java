package com.example.donkey.versus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class challengeChatActivityFragment extends Fragment {
    private User user;
    private Room roomProfile;

    TextView chatText;
    EditText nameText;
    EditText speakText;
    ScrollView scrollView;

    private chat c=new chat();
    private phpConnect p;

    public challengeChatActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_challenge_chat_activity, container, false);

        p=new phpConnect(getContext(),"read data");
        Bundle bundle=((challengeHomeActivity)getActivity()).getDataFromAvtivity();
       this.roomProfile=(Room)bundle.getSerializable("Room");
        this.user=(User)bundle.getSerializable("User");

        chatText = (TextView) view.findViewById(R.id.chatText);
        nameText=(EditText) view.findViewById(R.id.nameText);
        speakText=(EditText) view.findViewById(R.id.speakText);
        scrollView=(ScrollView)view.findViewById(R.id.s);
        nameText.setText(user.getName());
        p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
        p.addSendData("sql",String.format("SELECT * FROM chat WHERE `room_id`=%s",roomProfile.getRoomId()));
        p.execute(new GetUserCallback() {

            public void done(JSONArray jsonarray) {
                try{
                    String temp="";
                    JSONObject jsonobject;
                    for(int i=0;i<jsonarray.getJSONArray(0).length();i++){
                        jsonobject =jsonarray.getJSONArray(0).getJSONObject(i);
                        chat c=new chat();
                        c.setUserid(jsonobject.getString("name"));
                        c.setContent(jsonobject.getString("string"));
                        c.setTime(jsonobject.getString("time"));
                        temp = temp + "[" + c.getTime() + "]" + c.getUserid() + ":" + c.getContent() + "\n";

                    }
                    chatText.setText(temp);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("DebugLog",jsonarray.toString());

            }
        });


        Button button1 = (Button) view.findViewById(R.id.Button01);


        button1.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
                p.addSendData("sql0", String.format("insert into chat (`name`,`string`,`room_id`) values ('%s', '%s','%s')",user.getName(),speakText.getText().toString(),roomProfile.getRoomId()));
                p.addSendData("sql1",String.format("SELECT * FROM chat WHERE `room_id`=%s", roomProfile.getRoomId()));
                p.execute(new GetUserCallback() {
                    public void done(JSONArray jsonarray) {
                        Log.d("DebugLog",jsonarray.toString());
                        try {
                            String temp = "";
                            JSONObject jsonobject;
                            for (int i = 0; i < jsonarray.getJSONArray(2).length(); i++) {
                                jsonobject = jsonarray.getJSONArray(2).getJSONObject(i);
                                chat c = new chat();
                                c.setUserid(jsonobject.getString("name"));
                                c.setContent(jsonobject.getString("string"));
                                c.setTime(jsonobject.getString("time"));
                                temp = temp + "[" + c.getTime() + "]" + c.getUserid() + ":" + c.getContent() + "\n";

                            }
                            chatText.setText(temp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                });

                speakText.setText("");
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        return view;
    }

}