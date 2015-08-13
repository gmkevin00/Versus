package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;


public class inviteFriendActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,OnClickListener{
    private User user;
    private ListView inviteFriendList;
    private inviteFriendAdapter adapter;
    private HashMap<Integer, Boolean> friendSelectList=new HashMap<Integer, Boolean>();
    private Button inviteFriendConfirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        user= (User) bundle.getSerializable("User");

        inviteFriendList=(ListView)findViewById(R.id.inviteFriendList);
        adapter = new inviteFriendAdapter(inviteFriendActivity.this,user);
        adapter.setIsSelected(friendSelectList);
        for(int i=0;i<user.getUser_friendListId().size();i++){
            adapter.getIsSelected().put(i, false);
        };
        inviteFriendList.setAdapter(adapter);
        inviteFriendList.setOnItemClickListener(this);

        inviteFriendConfirmButton=(Button)findViewById(R.id.inviteFreindConfirmButton);
        inviteFriendConfirmButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invite_friend, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.getIsSelected().put(position, adapter.selectItem(view));
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(inviteFriendActivity.this, newRoomActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("newInviteList",adapter.getIsSelected());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
