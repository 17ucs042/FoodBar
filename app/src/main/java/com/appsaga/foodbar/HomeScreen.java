package com.appsaga.foodbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

public class HomeScreen extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener ,GoogleApiClient.OnConnectionFailedListener{

    //SliderLayout sliderLayout;
    // DefaultSliderView sliderView;

    private DrawerLayout drawerLayout;

    ImageView pic;
    private SliderLayout imageSlider;

    TextView name;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    Button special;
    String from;

    WormDotsIndicator wormdotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        from = "" + getIntent().getStringExtra("from");

        Log.d("fromIs:",from);
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
        /*sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5);
        setSliderViews();*/

       // imageSlider = findViewById(R.id.slider);
        special = findViewById(R.id.special);

       // HashMap<String,String> url_maps = new HashMap<>();

       // url_maps.put("Hannibal", "https://www.studytutorial.in/wp-content/uploads/2017/06/facebook_login_with_php-min.jpg");
        //url_maps.put("Big Bang Theory", "https://www.studytutorial.in/ wp-content/uploads/2016/02/maxresdefault-2.jpg");
        //url_maps.put("House of Cards", "https://www.studytutorial.in/wp-content/uploads/2016/10/multiseries_bar_chart.jpg");
       // url_maps.put("Game of Thrones", "https://www.studytutorial.in/wp-content/uploads/2017/04/studytutorial-logo.png");

        /*TreeMap<String,Index> file_maps = new TreeMap<>();
        file_maps.put("Hannibal",new Index(R.drawable.category1,0));
        file_maps.put("Big Bang Theory",new Index(R.drawable.category2,1));
        file_maps.put("House of Cards",new Index(R.drawable.category3,2));
        file_maps.put("Game of Thrones", new Index(R.drawable.category4,3));

        for(String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name).getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("Location",file_maps.get(name).getLocation());
            textSliderView.getBundle().putString("extra",name);

            imageSlider.addSlider(textSliderView);
        }

        imageSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(2000);
        imageSlider.addOnPageChangeListener(this);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        String userID = getIntent().getStringExtra("User Id");
        String my_name = getIntent().getStringExtra("name");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        pic = hView.findViewById(R.id.pic);
        name = hView.findViewById(R.id.name);

        //name.setText(my_name);

        if(from.equals("facebook")) {

            Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + userID + "/picture?type=large").into(pic);
        }

        ImageView category1 = findViewById(R.id.Category1);
        ImageView category2 = findViewById(R.id.Category2);
        ImageView category3 = findViewById(R.id.Category3);
        ImageView category4 = findViewById(R.id.Category4);
        ImageView category5 = findViewById(R.id.Category5);
        ImageView category6 = findViewById(R.id.Category6);

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

        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category1.class);
                startActivity(intent);
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category2.class);
                startActivity(intent);
            }
        });

        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category3.class);
                startActivity(intent);
            }
        });

        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category4.class);
                startActivity(intent);
            }
        });

        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category5.class);
                startActivity(intent);
            }
        });

        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, com.appsaga.foodbar.Category6.class);
                startActivity(intent);
            }
        });

        /*sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView sliderView) {

                Toast.makeText(HomeScreen.this, sliderView.getDescription(), Toast.LENGTH_SHORT).show();

                if (sliderView.getDescription().equals("1")) {

                    Toast.makeText(HomeScreen.this, "Clicked", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

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
                                                    HomeScreen.this.finish();
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

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeScreen.this,com.appsaga.foodbar.SpecialOffer.class));
            }
        });

        ViewPager viewPager = findViewById(R.id.viewpager);

        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        wormdotsIndicator = findViewById(R.id.worm_dots_indicator);

        wormdotsIndicator.setViewPager(viewPager);

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

    /*private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.slider1);
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(String.valueOf(i + 1));

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
