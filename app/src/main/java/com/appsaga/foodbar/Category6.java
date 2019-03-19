package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Category6 extends AppCompatActivity {

    ArrayList<Items> Category6;
    ListView Category6List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category6);

        Category6 = new ArrayList<>();

        Category6.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category6.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category6.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category6);

        Category6List = findViewById(R.id.Category6List);

        Category6List.setAdapter(myAdapter);

    }
}
