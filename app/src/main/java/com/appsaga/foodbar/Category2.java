package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Category2 extends AppCompatActivity {

    ArrayList<Items> Category2;
    ListView Category2List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        Category2 = new ArrayList<>();

        Category2.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category2.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category2.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category2);

        Category2List = findViewById(R.id.Category2List);

        Category2List.setAdapter(myAdapter);
    }
}
