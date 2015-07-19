package com.example.donkey.versus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView t1;
    Button b1;
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.textView);
        b1=(Button)findViewById(R.id.button1);
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
                        Log.d("FB", profile.getId());
                        Log.d("FB", profile.getName());
                        new GraphRequest(
                                accessToken.getCurrentAccessToken(),
                                "/me/friends",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {
                                    //        Log.d("FB","Members: " + response.toString());
                                            JSONObject jsonFriend = response.getJSONObject();
                                       //   Log.d("FB","Members: " + jsonFriend);
                                        try {
                                           Object keyName = jsonFriend.getJSONArray("data").getJSONObject(0).getString("name");
                                            Log.d("FB","Members: " + keyName);
                                        } catch (JSONException e) {
                                            Log.d("FB","Freind Get Failed!");
                                            e.printStackTrace();
                                        }
                                        //           Log.d("FB","Friend Members: " + keyName);

                                    }
                                }
                        ).executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("FB","Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("FB","fail");
                    }
                });
        Log.d("FB", "XDDDDDD");

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
        phpConnect p=new phpConnect();
        p.setUrl("http://140.115.80.235/~test/room.php");
        p.execute();
        try {
            JSONObject jsonobject = p.getJSON().getJSONObject(0);
            String text = jsonobject.getString("user_name");
            Log.d("php",text);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("php","fail");
        }
    }
}
