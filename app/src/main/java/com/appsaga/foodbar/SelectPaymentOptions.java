package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectPaymentOptions extends AppCompatActivity {

    Button confirmButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CustomerDatabaseHelper customerDatabaseHelper;
    ItemDatabaseHelper itemDatabaseHelper;

    HashMap<String, String> customerDetails = new HashMap<>();
    ArrayList<HashMap<String, String>> itemsOrdered = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_options);

        confirmButton = findViewById(R.id.confirm_button);

        final Address address = (Address)getIntent().getSerializableExtra("address");

        customerDatabaseHelper = new CustomerDatabaseHelper(SelectPaymentOptions.this);

        itemDatabaseHelper = new ItemDatabaseHelper(SelectPaymentOptions.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        customerDetails.put("Name", address.getName());
        customerDetails.put("Phone Number", address.getPhoneNum());
        customerDetails.put("House Number", address.getHouseNo());
        customerDetails.put("Area", address.getArea());
        customerDetails.put("Pincode", address.getPincode());
        customerDetails.put("Nickname", address.getNickname());

        Cursor data1 = itemDatabaseHelper.getAllData();

        if(data1.moveToFirst())
        {
            do
            {
                HashMap<String,String> order_value = new HashMap<>();

                order_value.put("Name", data1.getString(1));
                order_value.put("Quantity", data1.getString(2));
                order_value.put("Price", data1.getString(3));
                order_value.put("Number of items", String.valueOf(data1.getInt(4)));

                itemsOrdered.add(order_value);
            }while (data1.moveToNext());
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Confirming Order", "Please wait...", true);

                final Order order = new Order(customerDetails, itemsOrdered);

                databaseReference.child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int numChildren = (int) dataSnapshot.getChildrenCount();
                        databaseReference.child("orders").child(String.valueOf(numChildren+1)).setValue(order);

                        Toast.makeText(SelectPaymentOptions.this,"Order Confirmed",Toast.LENGTH_SHORT).show();

                        itemDatabaseHelper.deleteAllData();
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        dialog.dismiss();
                        Toast.makeText(SelectPaymentOptions.this,"Failed Placing Order",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }
}
