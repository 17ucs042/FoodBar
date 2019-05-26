package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowItems extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Item> allItems = new ArrayList<>();
    ListView itemsList;
    ItemAdapter itemAdapter;
    TextView title;
    TextView itemsNum;

    Toolbar toolbar;

    Boolean opened;
    ImageView searchIcon;

    ItemDatabaseHelper itemDatabaseHelper;

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

        itemDatabaseHelper = new ItemDatabaseHelper(ShowItems.this);

        opened = Boolean.TRUE;
        title.setText(value);

        toolbar.setTitle(R.string.app_name);

        DatabaseReference myRef = databaseReference.child("Categories").child(value);

        final ProgressDialog dialog = ProgressDialog.show(ShowItems.this, "Loading", "Please wait...", true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = (int)dataSnapshot.getChildrenCount();

                itemsNum = findViewById(R.id.itemsNum);

                if(count == 1)
                {
                    itemsNum.setText(Integer.toString(count)+" item");
                }
                else {
                    itemsNum.setText(Integer.toString(count) + " items");
                }
                if (opened == Boolean.TRUE) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Item item = ds.getValue(Item.class);
                        allItems.add(item);
                        itemAdapter = new ItemAdapter(ShowItems.this, allItems,ShowItems.this);
                        itemsList.setAdapter(null);
                        itemsList.setAdapter(itemAdapter);
                    }

                    opened = Boolean.FALSE;

                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dialog.dismiss();
                        }
                    },2000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchFragment fragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FrameLayout frameLayout = findViewById(R.id.frame_layout);
                frameLayout.removeAllViews();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
            }
        });
    }
}
