package com.example.donkey.versus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button loginButton;
    CallbackManager callbackManager;


    private User user;
    private ArrayList<Room> UserRoom=new ArrayList<Room>();
    private ArrayList<Join> UserJoin=new ArrayList<Join>();
    private ArrayList<Challenge> challengeSet=new ArrayList<Challenge>();
    private AccessToken accessToken;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

    }

    private FacebookCallback<LoginResult> callback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                getUserProfile();
                getUserFriend();
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
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginButton)
        {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
            LoginManager.getInstance().registerCallback(callbackManager, callback);
        }
    }
    public void userLoginDB(){


        phpConnect p=new phpConnect(this,"讀取資料中，請稍後...");

        p.setUrl(String.format("http://140.115.80.235/~group15/user.php?type=loginuser"));
        p.addSendData("uid", user.getFbid());
        p.addSendData("name", user.getName());
        p.addSendData("cloudMessageId",regid);
        p.execute(new GetUserCallback() {
            @Override
            public void done(JSONArray jsonarray) {
                Log.d("DebugLog",jsonarray.toString());
                try {
                    JSONObject jsonobject;
                    for (int i = 0; i < jsonarray.getJSONArray(0).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(0).getJSONObject(i);
                        Room r = new Room();
                        Log.d("DebugLog",""+Integer.parseInt(jsonobject.getString("room_id")));
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
                    for (int i = 0; i < jsonarray.getJSONArray(3).length(); i++) {
                        jsonobject = jsonarray.getJSONArray(3).getJSONObject(i);
                        Join j = new Join();
                        j.setJoinId(Integer.parseInt(jsonobject.getString("invite_id")));
                        j.setInviterId(jsonobject.getString("invite_inviter"));
                        j.setInviterName(jsonobject.getString("user_name"));
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
                    for (int i = 0; i < jsonarray.getJSONArray(1).length(); i++) {
                        Challenge challenge = new Challenge();
                        challenge.setTypeId(Integer.parseInt(jsonarray.getJSONArray(1).getJSONObject(i).getString("type_id")));
                        challenge.setTypeName(jsonarray.getJSONArray(1).getJSONObject(i).getString("type_name"));
                        if (!jsonarray.getJSONArray(2).isNull(i)) {
                            for (int j = 0; j < jsonarray.getJSONArray(2).getJSONArray(i).length(); j++) {
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

                Intent intent = new Intent(MainActivity.this, navigationDrawerHome.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Room", UserRoom);
                bundle.putSerializable("Join", UserJoin);
                bundle.putSerializable("User", user);
                bundle.putSerializable("Challenge", challengeSet);
                intent.putExtras(bundle);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    public void getUserFriend()
    {
        new GraphRequest(
                accessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        //Log.d("FB","Members: " + response.toString());
                        JSONObject jsonFriend = response.getJSONObject();
                        //Log.d("DebugLog","Members: " + jsonFriend);
                        try {
                            ArrayList<String> friendlistId = new ArrayList<String>();
                            ArrayList<String> friendlistName = new ArrayList<String>();
                            for (int i = 0; i < jsonFriend.getJSONArray("data").length() ; i++) {
                                friendlistId.add(jsonFriend.getJSONArray("data").getJSONObject(i).getString("id"));
                                friendlistName.add(jsonFriend.getJSONArray("data").getJSONObject(i).getString("name"));
                            }
                            user.setUserFriendListId(friendlistId);
                            user.setUserFriendListName(friendlistName);
                            setCloudMessege();
                        } catch (JSONException e) {
                            Log.d("DebugLog", "Freind Get Failed!");
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
    public void getUserProfile()
    {
        new GraphRequest(
                accessToken.getCurrentAccessToken(),
                "/me/",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject jsonProfile = response.getJSONObject();
                        try {
                            user = new User();
                            user.setUserFbid(jsonProfile.getString("id"));
                            user.setUserName(jsonProfile.getString("last_name") + jsonProfile.getString("first_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }


    public void setCloudMessege()
    {
        // Check device for Play Services APK.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = getRegistrationId(getApplicationContext());
            new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext()), new GetUserCallback() {
                @Override
                public void done(JSONArray jsonarray) {

                    try {
                        regid=jsonarray.get(0).toString();
                        userLoginDB();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }).execute();
        } else {
            Log.d("DebugLog", "No valid Google Play Services APK found.");
        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.d("DebugLog", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d("DebugLog", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());
        if (registeredVersion != currentVersion) {
            Log.d("DebugLog", "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}

