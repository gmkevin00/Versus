package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class joinActivity extends ActionBarActivity implements View.OnClickListener{
    private User user;
    private ArrayList<Join> UserJoin=new ArrayList<Join>();

    private RecyclerView joinList;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        this.user=(User)bundle.getSerializable("User");
        this.UserJoin=(ArrayList<Join>)bundle.getSerializable("Join");

     //   Log.d("DebugLog",""+UserJoin.size());

        joinList = (RecyclerView)findViewById(R.id.joinList);
        //joinList.setItemAnimator(new SlideInLeftAnimator());

        mLayoutManager = new LinearLayoutManager(this);
        joinList.setLayoutManager(mLayoutManager);

        joinAdapter j=new joinAdapter(user,UserJoin);
        joinList.setAdapter(j);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            backToRoomHome();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void backToRoomHome()
    {
        phpConnect p=new phpConnect(this,"讀取中，請稍後");
        p.setUrl(String.format("http://140.115.80.235/~group15/join.php"));
        p.addSendData("type", "leave");
        p.addSendData("user_id", user.getFbid());

        p.execute(new GetUserCallback() {
            @Override
            public void done(JSONArray jsonarray) {
                ArrayList<Room> UserRoom = new ArrayList<Room>();
                ArrayList<Room> InviteRoom = new ArrayList<Room>();
                try {
                    JSONObject jsonobject;
                    JSONObject inviteobject;
                    for (int i = 0; i < jsonarray.getJSONArray(0).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(0).getJSONObject(i);
                        Room r = new Room();
                        r.setRoomId(Integer.parseInt(jsonobject.getString("room_id")));
                        r.setRoomName(jsonobject.getString("room_name"));
                        r.setChallengeId(Integer.parseInt(jsonobject.getString("challenge_id")));
                        r.setChallengeName(jsonobject.getString("challenge_name"));
                        r.setTypeId(Integer.parseInt(jsonobject.getString("type_id")));
                        r.setTypeName(jsonobject.getString("type_name"));
                        r.setRoomCycle(jsonobject.getString("room_cycle"));
                        r.setRoomStart(jsonobject.getString("room_start"));
                        r.setRoomEnd(jsonobject.getString("room_end"));
                        r.setRoomStar(Integer.parseInt(jsonobject.getString("room_star")));
                        r.setRoomBoss(jsonobject.getString("room_boss"));
                        Log.d("DebugLog", jsonobject.getString("room_name"));
                        UserRoom.add(r);
                    }

                    for (int i = 0; i < jsonarray.getJSONArray(i).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(1).getJSONObject(i);
                        Room r = new Room();
                        r.setRoomId(Integer.parseInt(jsonobject.getString("room_id")));
                        r.setRoomName(jsonobject.getString("room_name"));
                        r.setChallengeId(Integer.parseInt(jsonobject.getString("challenge_id")));
                        r.setChallengeName(jsonobject.getString("challenge_name"));
                        r.setTypeId(Integer.parseInt(jsonobject.getString("type_id")));
                        r.setTypeName(jsonobject.getString("type_name"));
                        r.setRoomCycle(jsonobject.getString("room_cycle"));
                        r.setRoomStart(jsonobject.getString("room_start"));
                        r.setRoomEnd(jsonobject.getString("room_end"));
                        r.setRoomStar(Integer.parseInt(jsonobject.getString("room_star")));
                        r.setRoomBoss(jsonobject.getString("room_boss"));
                        InviteRoom.add(r);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent=new Intent();
                intent.setClass(joinActivity.this, RoomHomeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Room",UserRoom);
                bundle.putSerializable("Invite",InviteRoom);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        backToRoomHome();
    }
}
