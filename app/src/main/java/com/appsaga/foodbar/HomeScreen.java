package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class HomeScreen extends AppCompatActivity {

    SliderLayout sliderLayout;
    DefaultSliderView sliderView;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5);
        setSliderViews();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        ImageView category1 = findViewById(R.id.Category1);
        ImageView category2 = findViewById(R.id.Category2);
        ImageView category3 = findViewById(R.id.Category3);
        ImageView category4 = findViewById(R.id.Category4);
        ImageView category5 = findViewById(R.id.Category5);
        ImageView category6 = findViewById(R.id.Category6);

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

        sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(SliderView sliderView) {

                Toast.makeText(HomeScreen.this, sliderView.getDescription(), Toast.LENGTH_SHORT).show();

                if (sliderView.getDescription().equals("1")) {

                    Toast.makeText(HomeScreen.this, "Clicked", Toast.LENGTH_SHORT).show();
                }

            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
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

    private void setSliderViews() {

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
    }
}
