package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CancelEverydayOrder extends AppCompatActivity {

    CancelItemsAdapter cancelItemsAdapter;
    ArrayList<ItemsOrdered> cancelItems = new ArrayList<>();
    Address address;
    DatabaseReference databaseReference;
    ListView cancelItemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_everyday_order);

        address = (Address) getIntent().getSerializableExtra("address");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        cancelItemsListView = findViewById(R.id.cancel_items_list_view);

        final ProgressDialog dialog = ProgressDialog.show(CancelEverydayOrder.this, "Getting Orders", "Please wait...", true);

        databaseReference.child("Delivers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String branchPin = null;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                    for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(address.getPincode())) {
                            branchPin = entry.getKey();
                            break;
                        }
                    }
                }

                databaseReference.child("orders").child(branchPin).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("Cancell...","Yes1");
                        for(DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            HashMap<String,String> hashMap = (HashMap<String, String>) ds.child("customerDetails").getValue();

                            Log.d("Cancell...","Yes2");
                            Log.d("Cancell...",hashMap.get("Nickname"));
                            Log.d("Cancell...",address.getNickname());
                            if(hashMap.get("Name").equalsIgnoreCase(address.getName())&&
                                hashMap.get("Area").toLowerCase().contains(address.getArea().toLowerCase())&&
                                hashMap.get("Nickname").equalsIgnoreCase(address.getNickname())&&
                                hashMap.get("Phone Number").equalsIgnoreCase(address.getPhoneNum().replace("Ph: ","").trim())&&
                                hashMap.get("House Number").equalsIgnoreCase(address.getHouseNo())&&
                                hashMap.get("Pincode").equalsIgnoreCase(address.getPincode()))
                            {
                                for(DataSnapshot dataSnapshot1:ds.child("itemsOrdered").getChildren())
                                {
                                    cancelItems.add(dataSnapshot1.getValue(ItemsOrdered.class));
                                }
                            }
                        }

                        if(cancelItems.size()!=0)
                        {
                            cancelItemsAdapter = new CancelItemsAdapter(CancelEverydayOrder.this,cancelItems);
                            cancelItemsListView.setVisibility(View.VISIBLE);
                            cancelItemsListView.setAdapter(cancelItemsAdapter);
                            Log.d("Cancell...","Yes4");
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
