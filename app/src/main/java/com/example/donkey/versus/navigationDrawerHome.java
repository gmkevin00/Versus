package com.example.donkey.versus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;

public class navigationDrawerHome extends MaterialNavigationDrawer {

    private ArrayList<Room> RoomUser;
    private User user;
    private ArrayList<Challenge> challengeSet;

    private MenuItem entryInvitePage;
    private ArrayList<Join> UserJoin=new ArrayList<Join>();
    private RoomListAdapter adapter;

    private Target picLoadtarget;
    private Bitmap personalPhoto;
    private MaterialAccount account;

    @Override
    public void init(Bundle savedInstanceState) {
        this.disableLearningPattern();

        this.setDrawerBackgroundColor(Color.rgb(255, 255, 255));
        this.addSection(newSection("挑戰主頁", new RoomHomeActivity()));
        this.addSection(newSection("成就統計", new achievementFragment()));



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        this.RoomUser=(ArrayList<Room>)bundle.getSerializable("Room");
        this.UserJoin=(ArrayList<Join>)bundle.getSerializable("Join");
        challengeSet=(ArrayList<Challenge>)bundle.getSerializable("Challenge");
        user=(User)bundle.getSerializable("User");

       loadBitmap(String.format("https://graph.facebook.com/%s/picture", user.getFbid()));
       account = new MaterialAccount(this.getResources(),user.getName(),"gmkevin00@gmail.com",personalPhoto,R.drawable.drawerimg);
        this.addAccount(account);
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
            bundle.putSerializable("User", user);
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

    public void loadBitmap(String url) {
        if (picLoadtarget == null) picLoadtarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap b, Picasso.LoadedFrom from) {
                // do something with the Bitmap
                personalPhoto=b;
                changeAccount();


            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }

        };
        Picasso.with(this).load(url).into(picLoadtarget);
    }
    public void changeAccount()
    {
     account.setPhoto(personalPhoto);
        notifyAccountDataChanged();
        Log.d("DebugLog", "hello");
    }

}