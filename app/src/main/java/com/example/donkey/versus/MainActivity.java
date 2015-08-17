package com.example.donkey.versus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private View testButton;

    LoginButton loginButton;
    CallbackManager callbackManager;
    private User user;
    private ArrayList<Room> UserRoom=new ArrayList<Room>();
    private ArrayList<Join> UserJoin=new ArrayList<Join>();
    private ArrayList<Challenge> challengeSet=new ArrayList<Challenge>();
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        // App code
                        Profile profile = Profile.getCurrentProfile();
                        user = new User();
                        user.setUserFbid(profile.getId());
                        user.setUserName(profile.getName());
                        new GraphRequest(
                                accessToken.getCurrentAccessToken(),
                                "/me/friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                        //        Log.d("FB","Members: " + response.toString());
                                        JSONObject jsonFriend = response.getJSONObject();
                                          //Log.d("DebugLog","Members: " + jsonFriend);
                                        try {
                                            ArrayList<String> friendlistId = new ArrayList<String>();
                                            ArrayList<String> friendlistName = new ArrayList<String>();
                                            for (int i = 0; i < jsonFriend.length() - 1; i++) {
                                                friendlistId.add(jsonFriend.getJSONArray("data").getJSONObject(i).getString("id"));
                                                friendlistName.add(jsonFriend.getJSONArray("data").getJSONObject(i).getString("name"));
                                            }
                                            user.setUserFriendListId(friendlistId);
                                            user.setUserFriendListName(friendlistName);
                                            userLoginDB();
                                        } catch (JSONException e) {
                                            Log.d("DebugLog", "Freind Get Failed!");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        ).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("DebugLog", "Facebook Login cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("Log", "Facebook Login fail");
                    }
                });
            Context context=getBaseContext();

        testButton=(View)findViewById(R.id.button00);
        testButton.setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    }
    public void userLoginDB(){
        phpConnect p=new phpConnect(this,"讀取資料中,請稍後...");

        p.setUrl(String.format("http://140.115.80.235/~group15/user.php?type=loginuser&uid=%s&name=%s",user.getFbid(),user.getName()));
        p.execute(new GetUserCallback() {
            @Override
            public void done(JSONArray jsonarray) {
                //Log.d("DebugLog",jsonarray.toString());
                try {
                    JSONObject jsonobject;
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
                            UserRoom.add(r);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonobject;
                    for(int i=0;i<jsonarray.getJSONArray(3).length();i++){
                        jsonobject =jsonarray.getJSONArray(3).getJSONObject(i);
                        Join j=new Join();
                        j.setInviter(jsonobject.getString("invite_inviter"));
                        j.setJoinRoomId(jsonobject.getString("room_id"));
                        j.setJoinStart(jsonobject.getString("room_start"));
                        j.setJoinEnd(jsonobject.getString("room_end"));
                        j.setJoinName(jsonobject.getString("room_name"));
                        j.setJoinChallenge(jsonobject.getString("challenge_name"));
                        UserJoin.add(j);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    for(int i=0;i<jsonarray.getJSONArray(1).length();i++){
                        Challenge challenge=new Challenge();
                        challenge.setTypeId(Integer.parseInt(jsonarray.getJSONArray(1).getJSONObject(i).getString("type_id")));
                        challenge.setTypeName(jsonarray.getJSONArray(1).getJSONObject(i).getString("type_name"));
                        if(!jsonarray.getJSONArray(2).isNull(i)){
                            for(int j=0;j<jsonarray.getJSONArray(2).getJSONArray(i).length();j++){
                               challenge.addList(jsonarray.getJSONArray(2).getJSONArray(i).get(j).toString());
                            }
                        }
                        challengeSet.add(challenge);
                    }
                } catch (JSONException e) {
                    Log.d("DebugLog", e.toString());
                    e.printStackTrace();
                }
                //Log.d("DebugLog", jsonarray.getJSONArray(1).getJSONArray(1).toString());

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,RoomHomeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Room",UserRoom);
                bundle.putSerializable("User",user);
                bundle.putSerializable("Challenge",challengeSet);
                intent.putExtras(bundle);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}
