package com.appsaga.foodbar;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryAll extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private static final String TAG = "ListDataActivity";
    ArrayList<Items> CategoryAll = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_all);

        mDatabaseHelper = new DatabaseHelper(this);

        CategoryAll.clear();
        populateListView();
    }

    private void populateListView() {

        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getAllData();
        ListView CategoryAllList = findViewById(R.id.CategoryAllList);

        CategoryAll.clear();
        CategoryAllAdapter myCategoryAdapter = new CategoryAllAdapter(com.appsaga.foodbar.CategoryAll.this,CategoryAll);
        myCategoryAdapter.clear();

        myCategoryAdapter.notifyDataSetChanged();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayLis

            if(data.getInt(1)!=0 && !items.contains(data.getString(0))) {
                items.add(data.getString(0));
                CategoryAll.add(new Items(data.getString(0), data.getString(2), Boolean.TRUE, data.getInt(1)));
            }
        }
        //create the list adapter and set the adapter

        CategoryAllList.setAdapter(myCategoryAdapter);
    }
}
