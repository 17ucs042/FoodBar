package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Category5 extends AppCompatActivity {

    ArrayList<Items> Category5;
    ListView Category5List;
    CategoryAdapter myAdapter;

    FloatingActionButton shop;

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

        final TextView fab_text = findViewById( R.id.fab_text);
        fab_text.setVisibility(View.GONE);

        shop = findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price = "";

                for(Items item : Category5)
                {
                    if(item.getQuantity()!=0)
                    {
                        String price1  = (item.getQuantity() + "x" + item.getPrice()+ " = "+ (item.getQuantity()*Double.parseDouble(item.getPrice()))+"\n");
                        price = price + " " + price1;
                    }
                }

                Intent intent = new Intent(Category5.this,com.appsaga.foodbar.CategoryAll.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });

    }
}
