package com.appsaga.foodbar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Category2 extends AppCompatActivity {

    ArrayList<Items> Category2;
    ListView Category2List;
    CategoryAdapter myAdapter;

    FloatingActionButton shop;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        Category2 = new ArrayList<>();

        Category2.add(new Items("Milk", "100.00", Boolean.TRUE,0));
        Category2.add(new Items("Cake", "100.00", Boolean.TRUE,0));
        Category2.add(new Items("Pudding", "100.00", Boolean.FALSE,0));

        mDatabaseHelper=new DatabaseHelper(Category2.this);
        mDatabaseHelper.insertData("Milk",0
                ,"100.00");
        mDatabaseHelper.insertData("Cake",0
                ,"100.00");
        mDatabaseHelper.insertData("Pudding",0
                ,"100.00");

        myAdapter = new CategoryAdapter(this, Category2);

        Category2List = findViewById(R.id.Category2List);

        Category2List.setAdapter(myAdapter);

        shop=findViewById(R.id.shop);

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String price = "";

                for(Items item : Category2)
                {
                    if(item.getQuantity()!=0)
                    {
                        String price1  = (item.getQuantity() + "x" + item.getPrice()+ " = "+ (item.getQuantity()*Double.parseDouble(item.getPrice()))+"\n");
                        price = price + " " + price1;
                    }
                }

                Intent intent = new Intent(Category2.this,com.appsaga.foodbar.CategoryAll.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
    }

}
