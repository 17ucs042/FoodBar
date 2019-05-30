package com.appsaga.foodbar;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    String TAG = "CHECK....";

    private DrawerLayout drawerLayout;

    ImageView pic;
    TextView name;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    String from;

    WormDotsIndicator wormdotsIndicator;

    DatabaseHelper mDatabaseHelper;

    String placeName = "";
    TextView PlaceName;

    ViewPager viewPager;
    RelativeLayout searchFrame;

    TextView count;
    TabLayout tabLayout;
    ItemDatabaseHelper itemDatabaseHelper;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        from = "" +getIntent().getStringExtra("from");

        mDatabaseHelper=new DatabaseHelper(getApplicationContext());
        searchFrame = findViewById(R.id.search_frame);
        itemDatabaseHelper = new ItemDatabaseHelper(HomeScreen.this);

        viewPager = findViewById(R.id.viewpager);
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.categories);
        tabLayout.getTabAt(2).setIcon(R.drawable.search);
        tabLayout.getTabAt(3).setIcon(R.drawable.offers);
        tabLayout.getTabAt(4).setCustomView(R.layout.basket_custom_icon);

        if(from.equals("google"))
        {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        String userID = getIntent().getStringExtra("User Id");
        String my_name = getIntent().getStringExtra("name");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        pic = hView.findViewById(R.id.pic);
        name = hView.findViewById(R.id.name);

        if(from.equals("facebook")) {

            Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + userID + "/picture?type=large").into(pic);
        }

        //Log.d("Reference....",imagesRef+"");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        if(menuItem.getItemId()==R.id.log_out)
                        {
                            if(from.equals("facebook")) {
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(HomeScreen.this,com.appsaga.foodbar.MainActivity.class));
                                HomeScreen.this.finish();
                            }
                            else {
                                FirebaseAuth.getInstance().signOut();
                                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                        new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {
                                                if (status.isSuccess()) {
                                                    gotoMainActivity();
                                                } else {
                                                    Toast.makeText(HomeScreen.this, "Session not close", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        /*special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,com.appsaga.foodbar.SpecialOffer.class));
            }
        });*/

        LinearLayout getLocation = findViewById(R.id.getLocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(HomeScreen.this,EnterLocation.class),1);
            }
        });

        PlaceName = findViewById(R.id.placeName);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if( tab.getPosition()==4 )
                {
                    searchFrame.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                }
                else if(tab.getPosition()==2)
                {
                    searchFrame.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    searchFrame.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        count = tabLayout.getTabAt(4).getCustomView().findViewById(R.id.count);

        if(itemDatabaseHelper.getTotalItems()!=0)
        {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        }
        else
        {
            count.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                placeName = data.getStringExtra("Place Name");
                PlaceName.setText(placeName);
            }
        }*/

        if (requestCode == 10) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                if(result.equalsIgnoreCase("Home"))
                {
                    viewPager.setCurrentItem(0);
                }
                else if(result.equalsIgnoreCase("Categories"))
                {
                    Log.d("test....","Yese");
                    viewPager.setCurrentItem(1);
                }
                else if(result.equalsIgnoreCase("Search"))
                {
                    viewPager.setCurrentItem(2);
                }
                else if(result.equalsIgnoreCase("Offers"))
                {
                    viewPager.setCurrentItem(3);
                }
                else
                {
                    viewPager.setCurrentItem(4);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(from.equals("google")) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (opr.isDone()) {
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            name.setText(account.getDisplayName());
            try{
                Glide.with(this).load(account.getPhotoUrl()).into(pic);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        }else{
            //gotoMainActivity();
            HomeScreen.this.finish();
        }
    }
    private void gotoMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        // intent.putExtra("from","google");
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()!=0) {
            viewPager.setCurrentItem(0);
        }
        else if(viewPager.getCurrentItem()==0)
        {
            finish();
            //System.exit(0);
        }
        if(itemDatabaseHelper.getTotalItems()!=0)
        {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        }
        else
        {
            count.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDatabaseHelper.deleteData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(itemDatabaseHelper.getTotalItems()!=0)
        {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        }
        else
        {
            count.setVisibility(View.GONE);
        }

        if(dialog!=null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       // dialog = ProgressDialog.show(HomeScreen.this, "Loading", "Please wait...", true);
    }
}