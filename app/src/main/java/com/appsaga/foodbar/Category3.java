package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Category3 extends AppCompatActivity {

    ArrayList<Items> Category3;
    ListView Category3List;
    CategoryAdapter myAdapter;

    FloatingActionButton shop;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category3);

        Category3 = new ArrayList<>();

        Category3.add(new Items("Cadbury", "100.00", Boolean.TRUE,0));
        Category3.add(new Items("Amul", "100.00", Boolean.TRUE,0));
        Category3.add(new Items("Lotus", "100.00", Boolean.FALSE,0));

        mDatabaseHelper=new DatabaseHelper(Category3.this);
        mDatabaseHelper.insertData("Cadbury",0
                ,"100.00");
        mDatabaseHelper.insertData("Amul",0
                ,"100.00");
        mDatabaseHelper.insertData("Lotus",0
                ,"100.00");

        myAdapter = new CategoryAdapter(this, Category3);

        Category3List = findViewById(R.id.Category3List);

        Category3List.setAdapter(myAdapter);

        final TextView fab_text = findViewById( R.id.fab_text);
        fab_text.setVisibility(View.GONE);

        shop = findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price = "";

                for(Items item : Category3)
                {
                    if(item.getQuantity()!=0)
                    {
                        String price1  = (item.getQuantity() + "x" + item.getPrice()+ " = "+ (item.getQuantity()*Double.parseDouble(item.getPrice()))+"\n");
                        price = price + " " + price1;
                    }
                }

                Intent intent = new Intent(Category3.this,com.appsaga.foodbar.CategoryAll.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
    }
}
