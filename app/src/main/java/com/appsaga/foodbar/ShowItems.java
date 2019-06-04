package com.appsaga.foodbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class ShowItems extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Item> allItems = new ArrayList<>();
    ListView itemsList;
    ItemAdapter itemAdapter;
    TextView title;
    TextView itemsNum;
    ImageView backNavigate;

    Toolbar toolbar;

    Boolean opened;
    ImageView searchIcon;
    TextView count;
    TabLayout tabLayout;

    ItemDatabaseHelper itemDatabaseHelper;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        String value = getIntent().getStringExtra("value");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        itemsList = findViewById(R.id.itemsList);
        title = findViewById(R.id.itemsTitle);
        toolbar = findViewById(R.id.toolbar);
        searchIcon = findViewById(R.id.search_icon);
        backNavigate = findViewById(R.id.back_navigate);

        itemDatabaseHelper = new ItemDatabaseHelper(ShowItems.this);

        opened = Boolean.TRUE;
        title.setText(value);

        toolbar.setTitle(R.string.app_name);

        tabLayout = findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_icon).setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.categories).setText("Caegories"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.search).setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.offers).setText("Offers"));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.basket_custom_icon));

        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        count = tabLayout.getTabAt(4).getCustomView().findViewById(R.id.count);

        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        } else {
            count.setVisibility(View.GONE);
        }

        DatabaseReference myRef = databaseReference.child("Categories").child(value);

        final ProgressDialog dialog = ProgressDialog.show(ShowItems.this, "Loading", "Please wait...", true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = (int) dataSnapshot.getChildrenCount();

                itemsNum = findViewById(R.id.itemsNum);

                if (count == 1) {
                    itemsNum.setText(Integer.toString(count) + " item");
                } else {
                    itemsNum.setText(Integer.toString(count) + " items");
                }
                if (opened == Boolean.TRUE) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Item item = ds.getValue(Item.class);
                        allItems.add(item);

                    }

                    itemAdapter = new ItemAdapter(ShowItems.this, allItems);
                    itemsList.setAdapter(null);
                    itemsList.setAdapter(itemAdapter);

                    opened = Boolean.FALSE;

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dialog.dismiss();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        backNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        itemsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    tabLayout.setVisibility(View.GONE);
                }
                else if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                mLastFirstVisibleItem=firstVisibleItem;

            }
        });

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ((TextView)view.findViewById(R.id.name)).getText().toString();
                String quantity = ((Spinner)view.findViewById(R.id.quantity)).getSelectedItem().toString();
                String price = ((TextView)view.findViewById(R.id.price)).getText().toString();
                String type = ((TextView)view.findViewById(R.id.type)).getText().toString();
                Item item = (Item)parent.getItemAtPosition(position);

                Drawable drawable = ((ImageView)view.findViewById(R.id.display)).getDrawable();
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
                Bitmap image = bitmapDrawable .getBitmap();

                Intent intent = new Intent(ShowItems.this,ItemDescription.class);
                intent.putExtra("name",name);
                intent.putExtra("quantity",quantity);
                intent.putExtra("price",price);
                intent.putExtra("image",image);
                intent.putExtra("type",type);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    //Intent returnIntent = new Intent();
                    //returnIntent.putExtra("result", "Home");
                   // setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else if (tab.getPosition() == 1) {

                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Categories");
                    startActivity(intent);

                } else if (tab.getPosition() == 2) {

                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Search");
                    startActivity(intent);

                } else if (tab.getPosition() == 3) {
                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Offers");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Basket");
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    //Intent returnIntent = new Intent();
                    //returnIntent.putExtra("result", "Home");
                    //setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                } else if (tab.getPosition() == 1) {

                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Categories");
                    startActivity(intent);

                } else if (tab.getPosition() == 2) {

                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Search");
                    startActivity(intent);

                } else if (tab.getPosition() == 3) {
                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Offers");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                    intent.putExtra("result", "Basket");
                    startActivity(intent);
                }
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SearchFragment fragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FrameLayout frameLayout = findViewById(R.id.frame_layout);
                frameLayout.removeAllViews();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();*/
                Intent intent = new Intent(ShowItems.this,HomeScreen.class);
                intent.putExtra("result", "Search");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "Home");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        count = tabLayout.getTabAt(4).getCustomView().findViewById(R.id.count);

        if(itemAdapter!=null)
        {
            itemsList.setAdapter(null);
            itemsList.setAdapter(itemAdapter);
        }

        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
        } else {
            count.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
         //dialog = ProgressDialog.show(ShowItems.this, "Loading", "Please wait...", true);
    }
}