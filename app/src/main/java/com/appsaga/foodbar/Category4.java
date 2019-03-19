package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Category4 extends AppCompatActivity {

    ArrayList<Items> Category4;
    ListView Category4List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category4);

        Category4 = new ArrayList<>();

        Category4.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category4.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category4.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category4);

        Category4List = findViewById(R.id.Category4List);

        Category4List.setAdapter(myAdapter);
    }
}
