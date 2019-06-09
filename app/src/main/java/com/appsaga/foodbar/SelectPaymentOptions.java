package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    MyOrdersDatabaseHelper myOrdersDatabaseHelper;

    HashMap<String, String> customerDetails = new HashMap<>();
    ArrayList<HashMap<String, String>> itemsOrdered = new ArrayList<>();

    TextView cod;
    TextView upi;
    Address address;

    RadioButton rb1;
    RadioButton rb2;
    RelativeLayout codLayout;
    RelativeLayout upiLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_options);

        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setEnabled(Boolean.FALSE);

        cod = findViewById(R.id.COD);
        upi = findViewById(R.id.UPI);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        codLayout = findViewById(R.id.COD_layout);
        upiLayout = findViewById(R.id.UPI_layout);

        address = (Address)getIntent().getSerializableExtra("address");

        customerDatabaseHelper = new CustomerDatabaseHelper(SelectPaymentOptions.this);
        itemDatabaseHelper = new ItemDatabaseHelper(SelectPaymentOptions.this);
        myOrdersDatabaseHelper = new MyOrdersDatabaseHelper(SelectPaymentOptions.this);

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

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codLayout.setBackgroundColor(Color.parseColor("#FFC107"));
                upiLayout.setBackgroundColor(Color.WHITE);
                confirmButton.setEnabled(Boolean.TRUE);
                rb2.setChecked(false);
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upiLayout.setBackgroundColor(Color.parseColor("#FFC107"));
                codLayout.setBackgroundColor(Color.WHITE);
                confirmButton.setEnabled(Boolean.TRUE);
                rb1.setChecked(false);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rb1.isChecked()) {
                    final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Confirming Order", "Please wait...", true);

                    final Order order = new Order(customerDetails, itemsOrdered, "COD");

                    databaseReference.child("Delivers").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String branchPin = null;

                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                HashMap<String,String> hashMap = (HashMap<String,String>)ds.getValue();

                                for(HashMap.Entry<String,String> entry : hashMap.entrySet())
                                {
                                    if(entry.getValue().equalsIgnoreCase(address.getPincode()))
                                    {
                                        branchPin = entry.getKey();
                                        break;
                                    }
                                }
                            }

                            final String finalBranchPin = branchPin;
                            databaseReference.child("orders").child(branchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    int numChildren = (int) dataSnapshot.getChildrenCount();
                                    databaseReference.child("orders").child(finalBranchPin).child("Homy" + String.valueOf(numChildren + 1) + "Bee" + (int) (Math.random() * 100) + address.getName() + "Order" + address.getPincode() + (int) (Math.random() * 100)).setValue(order);

                                    Cursor data = itemDatabaseHelper.getAllData();

                                    myOrdersDatabaseHelper.insertData(data);
                                    Toast.makeText(SelectPaymentOptions.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                                    itemDatabaseHelper.deleteAllData();
                                    dialog.dismiss();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    dialog.dismiss();
                                    Toast.makeText(SelectPaymentOptions.this, "Failed Placing Order", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if(rb2.isChecked())
                {
                    Handler handler = new Handler();
                    final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Loading", "Please wait...", true);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            dialog.dismiss();

                            Uri uri =
                                    new Uri.Builder()
                                            .scheme("upi")
                                            .authority("pay")
                                            .appendQueryParameter("pa", "saranshgupta123456789.sg.sg@okhdfcbank")
                                            .appendQueryParameter("pn", "Ayush Gupta")
                                            .appendQueryParameter("tn", "Payment to Homy Bee")
                                            .appendQueryParameter("am", String.valueOf(itemDatabaseHelper.getTotalPrice()))
                                            .appendQueryParameter("cu", "INR")
                                            .build();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(uri);

                            Intent chooser = Intent.createChooser(intent,"Pay With");
                            if(null != chooser.resolveActivity(getPackageManager())) {
                                startActivityForResult(chooser, 10);
                            } else {
                                Toast.makeText(SelectPaymentOptions.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
                            }
                        }
                    },1500);
                }
                else
                {
                    Toast.makeText(SelectPaymentOptions.this,"Please select a payment option",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==10)
        {
            if(resultCode==RESULT_OK)
            {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            }
            else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(SelectPaymentOptions.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Confirming Order", "Please wait...", true);

                final Order order = new Order(customerDetails, itemsOrdered, "COD");

                databaseReference.child("Delivers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String branchPin = null;

                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            HashMap<String,String> hashMap = (HashMap<String,String>)ds.getValue();

                            for(HashMap.Entry<String,String> entry : hashMap.entrySet())
                            {
                                if(entry.getValue().equalsIgnoreCase(address.getPincode()))
                                {
                                    branchPin = entry.getKey();
                                    break;
                                }
                            }
                        }

                        final String finalBranchPin = branchPin;
                        databaseReference.child("orders").child(branchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                int numChildren = (int) dataSnapshot.getChildrenCount();
                                databaseReference.child("orders").child(finalBranchPin).child("Homy" + String.valueOf(numChildren + 1) + "Bee" + (int) (Math.random() * 100) + address.getName() + "Order" + address.getPincode() + (int) (Math.random() * 100)).setValue(order);

                                Cursor data = itemDatabaseHelper.getAllData();

                                myOrdersDatabaseHelper.insertData(data);
                                Toast.makeText(SelectPaymentOptions.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                                itemDatabaseHelper.deleteAllData();
                                dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                dialog.dismiss();
                                Toast.makeText(SelectPaymentOptions.this, "Failed Placing Order", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(SelectPaymentOptions.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(SelectPaymentOptions.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SelectPaymentOptions.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SelectPaymentOptions.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
