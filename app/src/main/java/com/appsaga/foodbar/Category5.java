package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Category5 extends AppCompatActivity {

    ArrayList<Items> Category5;
    ListView Category5List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category5);
        Category5 = new ArrayList<>();

        Category5.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category5.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category5.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category5);

        Category5List = findViewById(R.id.Category5List);

        Category5List.setAdapter(myAdapter);

    }
}
