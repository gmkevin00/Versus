package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;




public class RoomHomeActivity  extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemClickListener,OnItemClickListener {
    private ArrayList<Room> RoomUser;
    private User user;
    private ArrayList<Challenge> challengeSet;
    private ListView RoomListView;
    private Button addRoomButton;
    private RoomListAdapter adapter;
    private ArrayList<Competitor> CompetitorRoom = new ArrayList<Competitor>();
    private Room selectedRoom;

    private MenuItem entryInvitePage;
    private ArrayList<Join> UserJoin=new ArrayList<Join>();


    private HelpLiveo mHelpLiveo;

    private ArrayList<personalProcess> personalProcessList=new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        this.RoomUser=(ArrayList<Room>)bundle.getSerializable("Room");
        this.UserJoin=(ArrayList<Join>)bundle.getSerializable("Join");
        challengeSet=(ArrayList<Challenge>)bundle.getSerializable("Challenge");
        user=(User)bundle.getSerializable("User");
        RoomListView=(ListView)findViewById(R.id.RoomListView);
        RoomListView.setOnItemClickListener(this);

        adapter = new RoomListAdapter(RoomHomeActivity.this,RoomUser);
        RoomListView.setAdapter(adapter);
        addRoomButton=(Button)findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room_home, menu);
        entryInvitePage=menu.findItem(R.id.entryIinvitePage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.entryIinvitePage) {
            Intent intent=new Intent();
            intent.setClass(RoomHomeActivity.this, joinActivity.class);


            Bundle bundle=new Bundle();
            bundle.putSerializable("User",user);
            bundle.putSerializable("Join",UserJoin);
            intent.putExtras(bundle);
            int requestCode = 3;
            startActivityForResult(intent, requestCode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addRoomButton)
        {
            Intent intent=new Intent();
            intent.setClass(RoomHomeActivity.this,newRoomActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("User",user);
            bundle.putSerializable("Challenge",challengeSet);
            intent.putExtras(bundle);
            int requestCode = 8;
            startActivityForResult(intent, requestCode);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.RoomUser=(ArrayList<Room>)data.getExtras().getSerializable("Room");
        this.UserJoin=(ArrayList<Join>)data.getExtras().getSerializable("Invite");
        adapter.updateResults(RoomUser);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedRoom=(Room)adapter.getItem(position);
        phpConnect p=new phpConnect(this,"讀取資料中,請稍後...");
        p.setUrl(String.format("http://140.115.80.235/~group15/process.php"));
        p.addSendData("room_id", "" + selectedRoom.getRoomId());
        p.addSendData("user_id", user.getFbid());
        p.execute(new GetUserCallback() {
            @Override
            public void done(JSONArray jsonarray) {
                //Log.d("DebugLog", jsonarray.toString());

                try {
                    JSONObject jsonobject;
                    for (int i = 0; i < jsonarray.getJSONArray(0).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(0).getJSONObject(i);
                        boolean flag = false;

                        for (int j = 0; j < CompetitorRoom.size(); j++) {
                            if (CompetitorRoom.get(j).getFbid().equals(jsonobject.getString("user_uid"))) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            Competitor c = new Competitor();
                            c.setUserFbid(jsonobject.getString("user_uid"));
                            c.setUserName(jsonobject.getString("user_name"));
                            CompetitorRoom.add(c);
                        }

                    }

                    for (int i = 0; i < jsonarray.getJSONArray(1).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(1).getJSONObject(i);
                        for (int j = 0; j < CompetitorRoom.size(); j++) {
                            if (CompetitorRoom.get(j).getFbid().equals(jsonobject.getString("user_uid"))) {
                                if(Integer.parseInt(jsonobject.getString("process_check"))==1)
                                {
                                    CompetitorRoom.get(j).addTotalCount();
                                  //  Log.d("DebugLog", CompetitorRoom.get(j).getName() + "加一");
                                    break;
                                }
                            }
                        }

                        if(user.getFbid().equals(jsonobject.getString("user_uid")))
                        {
                            personalProcess pProcess=new personalProcess();
                            pProcess.setCheck(Integer.parseInt(jsonobject.getString("process_check")));
                           // Log.d("DebugLog", jsonobject.getString("process_check"));
                            pProcess.setTime(jsonobject.getString("process_time"));
                          //  Log.d("DebugLog", jsonobject.getString("process_time"));
                            personalProcessList.add(pProcess);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("DebugLog", "QAQ");
                }

                Intent intent = new Intent();
                intent.setClass(RoomHomeActivity.this, challengeHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", user);
                bundle.putSerializable("Room", selectedRoom);
                bundle.putSerializable("Competitor", CompetitorRoom);
                bundle.putSerializable("PersonalProcess", personalProcessList);

                intent.putExtras(bundle);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
            }
        });


    }

    public void onItemClick(int i) {
        FragmentManager mFragmentManager = getSupportFragmentManager();

    }




}
