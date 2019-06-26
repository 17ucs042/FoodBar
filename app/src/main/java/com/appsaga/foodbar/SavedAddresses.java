package com.appsaga.foodbar;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class SavedAddresses extends AppCompatActivity {

    CustomerDatabaseHelper customerDatabaseHelper;
    ListView savedList;
    Button addAddress;
    SavedAddressAdapter savedAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_addresses);

        customerDatabaseHelper = new CustomerDatabaseHelper(SavedAddresses.this);

        savedList = findViewById(R.id.saved_list);
        addAddress = findViewById(R.id.add_new_address);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(SavedAddresses.this,EnterDetails.class),10);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        Cursor data = customerDatabaseHelper.getAllData();
        savedAddressAdapter = new SavedAddressAdapter(SavedAddresses.this,data);
        savedList.setAdapter(null);
        savedList.setAdapter(savedAddressAdapter);
    }
}
