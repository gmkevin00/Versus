package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class RoomHomeActivity extends ActionBarActivity {
    private ArrayList<Room> RoomUser;
    private User user;
    private ListView RoomListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        RoomUser=(ArrayList<Room>)bundle.getSerializable("Room");
        user=(User)bundle.getSerializable("User");
        RoomListView=(ListView)findViewById(R.id.RoomListView);
        RoomListAdapter adapter = new RoomListAdapter(RoomHomeActivity.this,RoomUser);
        Log.d("DebugLog", RoomUser.get(0).getTypeName());
        RoomListView.setAdapter(adapter);
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
}
