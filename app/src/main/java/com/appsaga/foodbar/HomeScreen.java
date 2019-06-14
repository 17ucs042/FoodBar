package com.appsaga.foodbar;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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

public class HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LocationListener {

    String TAG = "CHECK....";

    private DrawerLayout drawerLayout;
    ImageView navButton;
    NavigationView navigationView;

    ImageView pic;
    TextView name;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    String from;
    String my_name = null;
    String userID = null;

    DatabaseHelper mDatabaseHelper;

    String placeName = "";
    TextView PlaceName;

    ViewPager viewPager;
    RelativeLayout searchFrame;

    TextView count;
    TabLayout tabLayout;
    ItemDatabaseHelper itemDatabaseHelper;
    CustomerDatabaseHelper customerDatabaseHelper;
    ProgressDialog dialog;
    MyPincodeDatabaseHelper myPincodeDatabaseHelper;
    MyOrdersDatabaseHelper myOrdersDatabaseHelper;
    FromDatabaseHelper fromDatabaseHelper;
    ImageView editPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        from = "" + getIntent().getStringExtra("from");

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        searchFrame = findViewById(R.id.search_frame);
        itemDatabaseHelper = new ItemDatabaseHelper(HomeScreen.this);
        myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(HomeScreen.this);
        customerDatabaseHelper = new CustomerDatabaseHelper(HomeScreen.this);
        myOrdersDatabaseHelper = new MyOrdersDatabaseHelper(HomeScreen.this);
        fromDatabaseHelper = new FromDatabaseHelper(HomeScreen.this);
        editPin = findViewById(R.id.edit_pin);

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

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        userID = getIntent().getStringExtra("User Id");
        my_name = getIntent().getStringExtra("name");

        if(fromDatabaseHelper.getTotalItems()==0)
        {
            fromDatabaseHelper.insertData(from,userID,my_name);
        }

        if (fromDatabaseHelper.getFrom().equals("google")) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        pic = hView.findViewById(R.id.pic);
        name = hView.findViewById(R.id.name);

        Log.d("Size...",fromDatabaseHelper.getTotalItems()+"");
        if (fromDatabaseHelper.getFrom().equals("facebook")) {

            Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + fromDatabaseHelper.getUserID() + "/picture?type=large").into(pic);
        }

        if(my_name!=null)
        {
            Log.d("name...",my_name);
        }

        PlaceName = findViewById(R.id.placeName);

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

        setLocation();

        drawerLayout = findViewById(R.id.drawer_layout);
        navButton = findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        if (menuItem.getItemId() == R.id.log_out) {
                            itemDatabaseHelper.deleteAllData();
                            customerDatabaseHelper.deleteAllData();
                            myPincodeDatabaseHelper.deleteAllData();
                            myOrdersDatabaseHelper.deleteAllData();
                            fromDatabaseHelper.deleteAllData();

                            if (from.equals("facebook")) {
                                LoginManager.getInstance().logOut();
                                startActivity(new Intent(HomeScreen.this, com.appsaga.foodbar.MainActivity.class));
                                HomeScreen.this.finish();
                            } else {
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
                        } else if (menuItem.getItemId() == R.id.homy_home) {
                            viewPager.setCurrentItem(0);
                        } else if (menuItem.getItemId() == R.id.categories) {
                            viewPager.setCurrentItem(1);
                        } else if (menuItem.getItemId() == R.id.search) {
                            viewPager.setCurrentItem(2);
                        } else if (menuItem.getItemId() == R.id.offers) {
                            viewPager.setCurrentItem(3);
                        } else if (menuItem.getItemId() == R.id.homy_basket) {
                            viewPager.setCurrentItem(4);
                        } else if (menuItem.getItemId() == R.id.address) {
                            startActivity(new Intent(HomeScreen.this, MyDeliveryAddress.class));
                        } else if (menuItem.getItemId() == R.id.my_orders) {
                            startActivity(new Intent(HomeScreen.this, MyOrders.class));
                        } else if (menuItem.getItemId() == R.id.about) {
                            startActivity(new Intent(HomeScreen.this, AboutUs.class));
                        }
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

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

                LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(HomeScreen.this, "Please Wait\nOR\nCheck Internet Connection", Toast.LENGTH_LONG).show();
                    setLocation();
                } else {

                    Toast.makeText(HomeScreen.this, "Please turn on GPS", Toast.LENGTH_LONG).show();
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 4 || tab.getPosition() == 2) {
                    searchFrame.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                } else if (tab.getPosition() == 3 || tab.getPosition() == 1) {
                    searchFrame.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                } else {
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

        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        } else {
            count.setVisibility(View.GONE);
        }

        editPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(HomeScreen.this, EnterPincode.class), 30);
            }
        });
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
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");

                if (result.equalsIgnoreCase("Home")) {
                    viewPager.setCurrentItem(0);
                } else if (result.equalsIgnoreCase("Categories")) {
                    viewPager.setCurrentItem(1);
                } else if (result.equalsIgnoreCase("Search")) {
                    viewPager.setCurrentItem(2);
                } else if (result.equalsIgnoreCase("Offers")) {
                    viewPager.setCurrentItem(3);
                } else if (result.equalsIgnoreCase("Basket")) {
                    viewPager.setCurrentItem(4);
                }
            }
        }

        if (requestCode == 30) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                myPincodeDatabaseHelper.insertData(result);
                PlaceName.setText(result);
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

        if (fromDatabaseHelper.getFrom().equals("google")) {
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

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            name.setText(Objects.requireNonNull(account).getDisplayName());
            try {
                Glide.with(this).load(account.getPhotoUrl()).into(pic);
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "image not found", Toast.LENGTH_LONG).show();
            }

        } else {
            //gotoMainActivity();
            HomeScreen.this.finish();
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        // intent.putExtra("from","google");
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        navigationView.setCheckedItem(R.id.menu_none);

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName()) && viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
        } else if (viewPager.getCurrentItem() == 0) {
            finishAffinity();
            finish();
        } else {
            super.onBackPressed();
        }
        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        } else {
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

        navigationView.setCheckedItem(R.id.menu_none);

        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        } else {
            count.setVisibility(View.GONE);
        }

        if (dialog != null) {
            dialog.dismiss();
        }

        String intent = getIntent().getStringExtra("intent");
        if (intent != null) {
            if (intent.equals("Basket")) {
                viewPager.setCurrentItem(4);
            } else if (intent.equals("Search")) {
                viewPager.setCurrentItem(2);
            }
        }

        String result = getIntent().getStringExtra("result");
        if (result != null) {
            if (result.equals("Categories")) {
                viewPager.setCurrentItem(1);
            } else if (result.equals("Search")) {
                viewPager.setCurrentItem(2);
            } else if (result.equals("Offers")) {
                viewPager.setCurrentItem(3);
            } else if (result.equals("Basket")) {
                viewPager.setCurrentItem(4);
            }
        }

        if (PlaceName.getText().toString().length() != 6) {
            if (myPincodeDatabaseHelper.getTotalItems() != 0) {
                PlaceName.setText(myPincodeDatabaseHelper.getPincode());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // dialog = ProgressDialog.show(HomeScreen.this, "Loading", "Please wait...", true);
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude;
        double longitude;

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Geocoder myLocation = new Geocoder(HomeScreen.this, Locale.getDefault());
            List<android.location.Address> myList = null;
            try {
                myList = myLocation.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (myList != null && myPincodeDatabaseHelper.getTotalItems()==0) {
                android.location.Address address = Objects.requireNonNull(myList).get(0);
                String addressStr = "";
                addressStr += address.getPostalCode();
                PlaceName.setText(addressStr);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setLocation() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, 2);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, HomeScreen.this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, 1);
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double latitude;
            double longitude;

            if (location != null && myPincodeDatabaseHelper.getTotalItems()==0) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder myLocation = new Geocoder(HomeScreen.this, Locale.getDefault());
                List<android.location.Address> myList = null;
                try {
                    myList = myLocation.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (myList != null) {
                    android.location.Address address = Objects.requireNonNull(myList).get(0);
                    String addressStr = "";
                    addressStr += address.getPostalCode();
                    PlaceName.setText(addressStr);
                    myPincodeDatabaseHelper.insertData(addressStr);
                }
            }
        } else {
            Toast.makeText(HomeScreen.this, "Please turn on GPS", Toast.LENGTH_LONG).show();
        }
    }
}