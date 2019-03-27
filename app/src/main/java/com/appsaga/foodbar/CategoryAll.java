package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryAll extends AppCompatActivity {

    ArrayList<Items> CategoryAll = new ArrayList<>();
    CategoryAllAdapter myCategoryAdapter;
    ListView CategoryAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_all);

        CategoryAll.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        CategoryAll.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        CategoryAll.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myCategoryAdapter = new CategoryAllAdapter(com.appsaga.foodbar.CategoryAll.this,CategoryAll);

        CategoryAllList = findViewById(R.id.CategoryAllList);

        CategoryAllList.setAdapter(myCategoryAdapter);
    }
}
