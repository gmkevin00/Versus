package com.example.donkey.versus;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class navigationDrawerHome extends MaterialNavigationDrawer {

    private MenuItem entryInvitePage;
    @Override
    public void init(Bundle savedInstanceState) {
        this.disableLearningPattern();
        this.setDrawerBackgroundColor(Color.rgb(255,236,179));
        this.addSection(newSection("Section 1",new RoomHomeActivity()));

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.menu_room_home, menu);
        entryInvitePage=menu.findItem(R.id.entryIinvitePage);
        return true;
    }
}