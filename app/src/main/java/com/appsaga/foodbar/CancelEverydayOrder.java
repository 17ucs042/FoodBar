package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CancelEverydayOrder extends AppCompatActivity {

    CustomerDatabaseHelper customerDatabaseHelper;
    ScheduledItemsDatabaseHelper scheduledItemsDatabaseHelper;
    TextView notAvailable;
    ListView cancelListView;
    DatabaseReference databaseReference;

    ArrayList<EverydayOrder> everydayOrders;
    CancelAdapter cancelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_everyday_order);

        customerDatabaseHelper = new CustomerDatabaseHelper(CancelEverydayOrder.this);
        scheduledItemsDatabaseHelper = new ScheduledItemsDatabaseHelper(CancelEverydayOrder.this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        notAvailable = findViewById(R.id.not_available);
        cancelListView = findViewById(R.id.cancel_list_view);

        if (scheduledItemsDatabaseHelper.getTotalItems() == 0 || customerDatabaseHelper.getTotalItems() == 0) {
            notAvailable.setVisibility(View.VISIBLE);
            notAvailable.setText("You don't have any everyday order scheduled\nOR\nYour Delivery Address is not set. Please set delivery address to view your everyday order schedule");
            cancelListView.setVisibility(View.GONE);
        } else {
            final ProgressDialog progressDialog = ProgressDialog.show(CancelEverydayOrder.this, "Loading", "Please Wait...");

            databaseReference.child("Delivers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String branchPin = null;

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                        for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
                            if (entry.getValue().equalsIgnoreCase(customerDatabaseHelper.getPincode())) {
                                branchPin = entry.getKey();
                                break;
                            }
                        }
                    }

                    final String finalBranchPin = branchPin;

                    databaseReference.child("orders").child(finalBranchPin).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                            int t = 0;
                            everydayOrders = new ArrayList<>();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Order order = ds.getValue(Order.class);
                                if (order.getCustomerDetails().get("Name").equalsIgnoreCase(customerDatabaseHelper.getName()) &&
                                        order.getCustomerDetails().get("Pincode").equalsIgnoreCase(customerDatabaseHelper.getPincode()) &&
                                        order.getCustomerDetails().get("Phone Number").equalsIgnoreCase(customerDatabaseHelper.getPhoneNumber()) &&
                                        order.getEveryday().equalsIgnoreCase("yes")) {
                                    t = 1;
                                    notAvailable.setVisibility(View.GONE);
                                    cancelListView.setVisibility(View.VISIBLE);

                                    everydayOrders.add(new EverydayOrder(order, finalBranchPin));
                                }
                            }

                            cancelAdapter = new CancelAdapter(CancelEverydayOrder.this, everydayOrders);
                            cancelListView.setAdapter(null);

                            if (everydayOrders.size() != 0) {
                                cancelListView.setAdapter(cancelAdapter);
                            }

                            if (t == 0) {
                                notAvailable.setVisibility(View.VISIBLE);
                                cancelListView.setVisibility(View.GONE);
                                notAvailable.setText("The saved delivery address/phone number or name does not match.\nPlease set correct delivery address");
                            }

                            progressDialog.dismiss();
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
}
