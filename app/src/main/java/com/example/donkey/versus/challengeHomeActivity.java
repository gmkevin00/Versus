package com.example.donkey.versus;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class challengeHomeActivity extends ActionBarActivity {
    private Room roomProfile;
    private User user;
    private ArrayList<Competitor> competitors=new ArrayList<Competitor>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_home);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        this.roomProfile=(Room)bundle.getSerializable("Room");
        this.user=(User)bundle.getSerializable("User");
        this.competitors=(ArrayList)bundle.getSerializable("Competitor");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge_home, menu);
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
