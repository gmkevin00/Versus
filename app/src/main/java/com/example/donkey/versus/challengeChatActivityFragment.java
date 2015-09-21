package com.example.donkey.versus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class challengeChatActivityFragment extends Fragment {
    private User user;
    private Room roomProfile;

    private EditText nameText;
    private EditText speakText;
    private Button sendChatButton;

    private RecyclerView chatRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private chatAdapter chatAdapter;

    private ArrayList<chat> chatList=new ArrayList();
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

        nameText=(EditText) view.findViewById(R.id.nameText);
        speakText=(EditText) view.findViewById(R.id.speakText);
        nameText.setText(user.getName());

        chatRecyclerView = (RecyclerView)view.findViewById(R.id.chatRecyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        chatRecyclerView.setLayoutManager(mLayoutManager);

        chatAdapter=new chatAdapter(chatList,user);
        chatRecyclerView.setAdapter(chatAdapter);

        p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
        p.addSendData("sql", String.format("SELECT * FROM chat WHERE `room_id`=%s", roomProfile.getRoomId()));
        p.execute(new GetUserCallback() {

            public void done(JSONArray jsonarray) {
                try {
                    chatList=new ArrayList<chat>();
                    JSONObject jsonobject;
                    for (int i = 0; i < jsonarray.getJSONArray(0).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(0).getJSONObject(i);
                        chat c = new chat();
                        c.setUserid(jsonobject.getString("name"));
                        c.setContent(jsonobject.getString("string"));
                        c.setTime(jsonobject.getString("time"));
                        chatList.add(c);
                    }

                    chatAdapter=new chatAdapter(chatList,user);
                    chatRecyclerView.setAdapter(chatAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        sendChatButton = (Button) view.findViewById(R.id.sendChatButton);


        sendChatButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
                p.addSendData("sql0", String.format("insert into chat (`name`,`string`,`room_id`) values ('%s', '%s','%s')",user.getFbid(),speakText.getText().toString(),roomProfile.getRoomId()));
                p.addSendData("sql1", String.format("SELECT * FROM chat WHERE `room_id`=%s", roomProfile.getRoomId()));
                p.execute(new GetUserCallback() {
                    public void done(JSONArray jsonarray) {
                        try {
                            chatList = new ArrayList<chat>();
                            JSONObject jsonobject;
                            for (int i = 0; i < jsonarray.getJSONArray(2).length(); i++) {
                                jsonobject = jsonarray.getJSONArray(2).getJSONObject(i);
                                chat c = new chat();
                                c.setUserid(jsonobject.getString("name"));
                                c.setContent(jsonobject.getString("string"));
                                c.setTime(jsonobject.getString("time"));
                                chatList.add(c);
                            }
                            chatAdapter=new chatAdapter(chatList,user);
                            chatRecyclerView.setAdapter(chatAdapter);

                        } catch (JSONException e) {
                            Log.d("DebugLog","error");
                            e.printStackTrace();
                        }
                    }
                });

                speakText.setText("");
            }
        });

        return view;
    }

}