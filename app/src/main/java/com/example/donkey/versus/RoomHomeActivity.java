package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
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

    private HelpLiveo mHelpLiveo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        this.RoomUser=(ArrayList<Room>)bundle.getSerializable("Room");
        challengeSet=(ArrayList<Challenge>)bundle.getSerializable("Challenge");
        user=(User)bundle.getSerializable("User");
        RoomListView=(ListView)findViewById(R.id.RoomListView);
        adapter = new RoomListAdapter(RoomHomeActivity.this,RoomUser);
        RoomListView.setAdapter(adapter);
        addRoomButton=(Button)findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(this);

        mHelpLiveo = new HelpLiveo();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room_home, menu);
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



    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(RoomHomeActivity.this,newRoomActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("User",user);
        bundle.putSerializable("Challenge",challengeSet);
        intent.putExtras(bundle);
        int requestCode = 8;
        startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.RoomUser=(ArrayList<Room>)data.getExtras().getSerializable("Room");

        adapter.updateResults(RoomUser);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Room r=(Room)adapter.getItem(position);

        phpConnect p=new phpConnect(this,"讀取資料中,請稍後...");

        p.setUrl(String.format("http://140.115.80.235/~group15/challenge.php"));
        p.addSendData("room_id",""+r.getRoomId());
        p.execute(new GetUserCallback() {
            @Override
            public void done(JSONArray jsonarray) {
                try {
                    JSONObject jsonobject;
                    for (int i = 0; i < jsonarray.getJSONArray(0).length(); i++) {


                    }
                } catch (JSONException e) {
                     e.printStackTrace();
                }
            }
        });
                Intent intent = new Intent();
                intent.setClass(RoomHomeActivity.this, challengeHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", user);
                intent.putExtras(bundle);
                int requestCode = 14;
                startActivityForResult(intent, requestCode);
            }

    public void onItemClick(int i) {
        FragmentManager mFragmentManager = getSupportFragmentManager();

    }



}
