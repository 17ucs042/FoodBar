package com.appsaga.foodbar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    //private static final String EMAIL = "email";
    ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();

        int delay = 1500;

        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, com.appsaga.foodbar.HomeScreen.class);
                startActivity(intent);
                finish();
            }
        }, delay);*/

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        profilePictureView = findViewById(R.id.pro_pic);

        final TextView pro_name = findViewById(R.id.pro_name);

        //loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String userID = loginResult.getAccessToken().getUserId();

                profilePictureView.setProfileId(userID);

                profilePictureView.setVisibility(View.VISIBLE);

                /*String response = FacebookSdk.request(userId);

                JSONObject json = Util.parseJson(response);
                String name = json.getString("name");

                Log.d("First Name: ",profile.getFirstName());

                pro_name.setText("Name: "+profile.getFirstName()+" "+profile.getLastName());*/

                Intent intent = new Intent(MainActivity.this,com.appsaga.foodbar.HomeScreen.class);
                intent.putExtra("User Id",userID);

                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
