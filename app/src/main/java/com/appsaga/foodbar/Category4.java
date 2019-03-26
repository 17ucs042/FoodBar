package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Category4 extends AppCompatActivity {

    ArrayList<Items> Category4;
    ListView Category4List;
    CategoryAdapter myAdapter;

    FloatingActionButton shop;

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

        shop = findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price = "";

                for(Items item : Category4)
                {
                    if(item.getQuantity()!=0)
                    {
                        String price1  = (item.getQuantity() + "x" + item.getPrice()+ " = "+ (item.getQuantity()*Double.parseDouble(item.getPrice()))+"\n");
                        price = price + " " + price1;
                    }
                }

                Intent intent = new Intent(Category4.this,com.appsaga.foodbar.Payment.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
    }
}
