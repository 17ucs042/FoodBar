package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Category3 extends AppCompatActivity {

    ArrayList<Items> Category3;
    ListView Category3List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category3);

        Category3 = new ArrayList<>();

        Category3.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category3.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category3.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category3);

        Category3List = findViewById(R.id.Category3List);

        Category3List.setAdapter(myAdapter);
    }
}
