package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class joinActivity extends ActionBarActivity {
    private User user;
    private ArrayList<Join> UserJoin=new ArrayList<Join>();

    private RecyclerView joinList;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        this.user=(User)bundle.getSerializable("User");
        this.UserJoin=(ArrayList<Join>)bundle.getSerializable("Join");

        Log.d("DebugLog",""+UserJoin.size());

        joinList = (RecyclerView)findViewById(R.id.joinList);
        //joinList.setItemAnimator(new SlideInLeftAnimator());

        mLayoutManager = new LinearLayoutManager(this);
        joinList.setLayoutManager(mLayoutManager);

        joinAdapter j=new joinAdapter(UserJoin);
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
}
