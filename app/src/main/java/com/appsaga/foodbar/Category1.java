package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

    FloatingActionButton shop;

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

        shop = findViewById(R.id.shop);

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

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price = "";
                Log.d("Checkpoint","Yes");
                for(Items item : Category1)
                {
                    Log.d("Checkpoint","YES");
                    if(item.getQuantity()!=0)
                    {
                        Log.d("Checkpoint","Yes1");
                        String price1  = (item.getQuantity() + "x" + item.getPrice()+ " = "+ (item.getQuantity()*Double.parseDouble(item.getPrice()))+"\n");
                        price = price + " " + price1;
                    }
                }

                Log.d("Checkpoint","YES1");
                Intent intent = new Intent(Category1.this,com.appsaga.foodbar.Payment.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
