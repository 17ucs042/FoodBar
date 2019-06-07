package com.appsaga.foodbar;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MyOrders extends AppCompatActivity {

    ListView myOrdersList;
    MyOrdersAdapter myOrdersAdapter;
    MyOrdersDatabaseHelper myOrdersDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        myOrdersDatabaseHelper = new MyOrdersDatabaseHelper(MyOrders.this);
        Cursor data = myOrdersDatabaseHelper.getAllData();

        myOrdersList = findViewById(R.id.my_orders_list);

        if(data.getCount()!=0) {

             myOrdersAdapter = new MyOrdersAdapter(MyOrders.this,data);
            myOrdersList.setAdapter(myOrdersAdapter);
        }
    }
}
