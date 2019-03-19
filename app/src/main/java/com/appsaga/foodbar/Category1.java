package com.appsaga.foodbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Category1 extends AppCompatActivity {

    ArrayList<Items> Category1;
    ListView Category1List;
    CategoryAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);

        Category1 = new ArrayList<>();

        Category1.add(new Items("Burger", "100.00", Boolean.TRUE,0));
        Category1.add(new Items("Pizza", "100.00", Boolean.TRUE,0));
        Category1.add(new Items("Chowmein", "100.00", Boolean.FALSE,0));

        myAdapter = new CategoryAdapter(this, Category1);

        Category1List = findViewById(R.id.Category1List);

        Category1List.setAdapter(myAdapter);

        /*Category1List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Items items = Category1.get(position);

                Button order = view.findViewById(R.id.inStock);

                if (items.getInStock() == Boolean.TRUE) {
                    Toast.makeText(Category1.this, items.getName(), Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }
}
