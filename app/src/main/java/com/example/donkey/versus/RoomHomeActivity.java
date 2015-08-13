package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class RoomHomeActivity extends ActionBarActivity implements View.OnClickListener {
    private ArrayList<Room> RoomUser;
    private User user;
    private ArrayList<Challenge> challengeSet;
    private ListView RoomListView;
    private Button addRoomButton;
    private RoomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
