package com.appsaga.foodbar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SelectCancelOrderAddress extends AppCompatActivity {

    CustomerDatabaseHelper customerDatabaseHelper;
    ListView savedList;
    CancelledAddressAdpater cancelledAddressAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cancel_order_address);

        customerDatabaseHelper = new CustomerDatabaseHelper(SelectCancelOrderAddress.this);

        savedList = findViewById(R.id.saved_list);
    }

    @Override
    protected void onResume() {

        super.onResume();

        Cursor data = customerDatabaseHelper.getAllData();
        cancelledAddressAdpater = new CancelledAddressAdpater(SelectCancelOrderAddress.this,data);
        savedList.setAdapter(null);
        savedList.setAdapter(cancelledAddressAdpater);
    }
}
