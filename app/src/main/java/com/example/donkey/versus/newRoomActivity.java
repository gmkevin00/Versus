package com.example.donkey.versus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;


public class newRoomActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {
    private User user;
    private ArrayList<Challenge> challengeSet;
    private ArrayList challengeTypeList=new ArrayList();
    private ArrayList randomChallengeList=new ArrayList();

    private TextView newRoomName;

    private Spinner challengeTypeSpinner;
    private Button getRandomChallengeButton;
    private TextView newChallengeName;
    private int challengeType;

    private  Calendar calendar;
    private Button newRoomStart;
    private Button newRoomEnd;
    private int dateSetter;

    private String[] cycleList={"每日一次","每週一次","每月一次"};
    private Spinner cycleSpinner;
    private int newRoomCycle;

    private Button inviteButton;
    private ArrayList newInviteFriend=new ArrayList();
    private JSONArray friendJson;

    private Button addRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        challengeSet=(ArrayList<Challenge>)bundle.getSerializable("Challenge");
        user=(User)bundle.getSerializable("User");

        newRoomName=(TextView)findViewById(R.id.newRoomName);

        challengeTypeSpinner=(Spinner)findViewById(R.id.newChallengeType);
        newChallengeName=(TextView)findViewById(R.id.newChallengeName);
        for(int i=0;i<challengeSet.size();i++)
        {
            challengeTypeList.add(challengeSet.get(i).getTypeName());
        }
        ArrayAdapter challengeTypeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, challengeTypeList);
        challengeTypeSpinner.setAdapter(challengeTypeAdapter);
        challengeTypeSpinner.setOnItemSelectedListener(this);


        randomChallengeList=challengeSet.get(0).getTypeList();
        getRandomChallengeButton=(Button)findViewById(R.id.getRandomChallengeButton);
        getRandomChallengeButton.setOnClickListener(this);

        calendar=Calendar.getInstance();
        newRoomStart=(Button)findViewById(R.id.newRoomStart);
        newRoomEnd=(Button)findViewById(R.id.newRoomEnd);
        newRoomStart.setOnClickListener(this);
        newRoomEnd.setOnClickListener(this);

        ArrayAdapter cycleAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cycleList);
        cycleSpinner=(Spinner)findViewById(R.id.cycleSpinner);
        cycleSpinner.setAdapter(cycleAdapter);
        cycleSpinner.setOnItemSelectedListener(this);

        inviteButton=(Button)findViewById(R.id.inviteButton);
        inviteButton.setOnClickListener(this);

        addRoomButton=(Button)findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_room, menu);
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
        if(v.getId()==R.id.getRandomChallengeButton){
            if(!randomChallengeList.isEmpty()) {
                Random r = new Random();
                newChallengeName.setText("" + randomChallengeList.get(r.nextInt(randomChallengeList.size())));
            }
        }

        if(v.getId()==R.id.newRoomStart){
            new DatePickerDialog(this,this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            dateSetter=0;
        }

        if(v.getId()==R.id.newRoomEnd){
            new DatePickerDialog(this,this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            dateSetter=1;
        }

        if(v.getId()==R.id.inviteButton){
            Intent intent=new Intent();
            intent.setClass(newRoomActivity.this, inviteFriendActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("User", user);
            intent.putExtras(bundle);
            int requestCode=101;
            startActivityForResult(intent, requestCode);
        }

        if(v.getId()==R.id.addRoomButton){
            phpConnect p=new phpConnect(this,"讀取資料中,請稍後...");
            p.addSendData("user",user.getFbid());
            p.addSendData("name",newRoomName.getText().toString());
            p.addSendData("type",""+challengeType);
            p.addSendData("challenge",newChallengeName.getText().toString());
            p.addSendData("start",newRoomStart.getText().toString());
            p.addSendData("end",newRoomEnd.getText().toString());
            p.addSendData("cycle",""+newRoomCycle);
            p.addSendData("friend", friendJson.toString());
            p.setUrl("http://140.115.80.235/~group15/room.php?type=room_add");
            p.execute(new GetUserCallback() {
                @Override
                public void done(JSONArray jsonarray) {
                    ArrayList<Room> UserRoom=new ArrayList<Room>();
                    ArrayList<Room> InviteRoom=new ArrayList<Room>();
                    try {
                        JSONObject jsonobject;
                        JSONObject inviteobject;
                        for(int i=0;i<jsonarray.getJSONArray(0).length();i++){
                            jsonobject =jsonarray.getJSONArray(0).getJSONObject(i);
                            Room r=new Room();
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
                            Log.d("DebugLog",jsonobject.getString("room_name"));
                            UserRoom.add(r);
                        }

                        for(int i=0;i<jsonarray.getJSONArray(i).length();i++){
                            jsonobject =jsonarray.getJSONArray(1).getJSONObject(i);
                            Room r=new Room();
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
                            Log.d("DebugLog",jsonobject.getString("room_name"));
                            InviteRoom.add(r);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent=new Intent();
                    intent.setClass(newRoomActivity.this, RoomHomeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Room",UserRoom);
                    bundle.putSerializable("Invite",InviteRoom);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.newChallengeType){
            randomChallengeList=challengeSet.get(position).getTypeList();
            challengeType=challengeSet.get(position).getTypeId();
            newChallengeName.setText("");
        }
        if(parent.getId()==R.id.cycleSpinner){
            if(position==0)
            {
                newRoomCycle=1;
            }
            else if(position==1)
            {
                newRoomCycle=7;
            }
            else if(position==30)
            {
                newRoomCycle=30;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(dateSetter==0){
            newRoomStart.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
        }
        if(dateSetter==1){
            newRoomEnd.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.newInviteFriend=new ArrayList();
        this.friendJson=new JSONArray();
        HashMap<Integer, Boolean> friendSelectList=(HashMap)data.getExtras().getSerializable("newInviteList");
        for(int i=0; i< friendSelectList.size();i++){
            if(friendSelectList.get(i))
            {
                newInviteFriend.add(user.getUser_friendListId().get(i));
            }
        }
        if(newInviteFriend.size()!=0)
        {
            this.friendJson = new JSONArray(Arrays.asList(newInviteFriend));
            inviteButton.setText(String.format("已邀請%d位朋友",newInviteFriend.size()));
        }
        else
        {
            inviteButton.setText("邀請朋友");
        }
    }

}
