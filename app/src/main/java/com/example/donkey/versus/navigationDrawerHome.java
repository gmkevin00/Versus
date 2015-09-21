package com.example.donkey.versus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class navigationDrawerHome extends MaterialNavigationDrawer {

    private ArrayList<Room> RoomUser;
    private User user;
    private ArrayList<Challenge> challengeSet;

    private MenuItem entryInvitePage;
    private ArrayList<Join> UserJoin=new ArrayList<Join>();
    private RoomListAdapter adapter;
    @Override
    public void init(Bundle savedInstanceState) {
        this.disableLearningPattern();
        this.setDrawerBackgroundColor(Color.rgb(255, 255, 255));
        this.addSection(newSection("Section 1", new RoomHomeActivity()));

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.RoomUser=(ArrayList<Room>)bundle.getSerializable("Room");
        this.UserJoin=(ArrayList<Join>)bundle.getSerializable("Join");
        challengeSet=(ArrayList<Challenge>)bundle.getSerializable("Challenge");
        user=(User)bundle.getSerializable("User");


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.menu_room_home, menu);
        entryInvitePage=menu.findItem(R.id.entryIinvitePage);
        return true;
    }

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
            intent.setClass(getBaseContext(), joinActivity.class);


            Bundle bundle=new Bundle();
            bundle.putSerializable("User",user);
            bundle.putSerializable("Join",UserJoin);
            intent.putExtras(bundle);
            int requestCode = 8;
            startActivityForResult(intent, requestCode);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==8)
        {
            Log.d("DebugLog", "XDDDD");
            this.RoomUser=(ArrayList<Room>)data.getExtras().getSerializable("Room");
            this.UserJoin=(ArrayList<Join>)data.getExtras().getSerializable("Invite");
            adapter.updateResults(RoomUser);
        }
    }

}