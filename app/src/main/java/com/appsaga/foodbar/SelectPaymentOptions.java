package com.appsaga.foodbar;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    ScheduledItemsDatabaseHelper scheduledItemsDatabaseHelper;

    HashMap<String, String> customerDetails = new HashMap<>();
    ArrayList<HashMap<String, String>> itemsOrdered = new ArrayList<>();

    TextView cod;
    TextView upi;
    Address address;

    RadioButton rb1;
    RadioButton rb2;
    RelativeLayout codLayout;
    RelativeLayout upiLayout;

    String scheduled;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_options);

        scheduled = getIntent().getStringExtra("scheduledEveryday");
        address = (Address) getIntent().getSerializableExtra("address");
        time = getIntent().getStringExtra("time");

        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setEnabled(Boolean.FALSE);

        cod = findViewById(R.id.COD);
        upi = findViewById(R.id.UPI);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        codLayout = findViewById(R.id.COD_layout);
        upiLayout = findViewById(R.id.UPI_layout);

        customerDatabaseHelper = new CustomerDatabaseHelper(SelectPaymentOptions.this);
        itemDatabaseHelper = new ItemDatabaseHelper(SelectPaymentOptions.this);
        myOrdersDatabaseHelper = new MyOrdersDatabaseHelper(SelectPaymentOptions.this);
        scheduledItemsDatabaseHelper = new ScheduledItemsDatabaseHelper(SelectPaymentOptions.this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        customerDetails.put("Name", address.getName());
        customerDetails.put("Phone Number", address.getPhoneNum().replace("Ph: ","").trim());
        customerDetails.put("House Number", address.getHouseNo());
        customerDetails.put("Area", address.getArea());
        customerDetails.put("Pincode", address.getPincode());
        customerDetails.put("Nickname", address.getNickname());

        Cursor data1 = itemDatabaseHelper.getAllData();

        if (data1.moveToFirst()) {
            do {
                HashMap<String, String> order_value = new HashMap<>();

                order_value.put("Name", data1.getString(1));
                order_value.put("Quantity", data1.getString(2));
                order_value.put("Price", data1.getString(3));
                order_value.put("Number_of_items", String.valueOf(data1.getInt(4)));

                itemsOrdered.add(order_value);
            } while (data1.moveToNext());
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

                if (isConnectionAvailable(SelectPaymentOptions.this)) {
                    if (rb1.isChecked()) {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_VIEW);
                        Uri uri = Uri.parse("upi://pay").buildUpon()
                                        .appendQueryParameter("pa", "saranshgupta123456789.sg.sg@okhdfcbank")
                                        .appendQueryParameter("pn", "Garvit")
                                        .appendQueryParameter("tn", "Payment to Homy Bee")
                                        .appendQueryParameter("am", "5")
                                        .appendQueryParameter("cu", "INR")
                                        .build();
                        sendIntent.setData(uri);
                        startActivity(Intent.createChooser(sendIntent, "Yes"));

                        /*Uri uri =
                                new Uri.Builder()
                                        .scheme("upi")
                                        .authority("pay")
                                        .appendQueryParameter("pa", "saranshgupta123456789.sg.sg@okhdfcbank")
                                        .appendQueryParameter("pn", "Garvit")
                                        .appendQueryParameter("tn", "Payment to Homy Bee")
                                        .appendQueryParameter("am", "5")
                                        .appendQueryParameter("cu", "INR")
                                        .build();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);

                        Intent chooser = Intent.createChooser(intent, "Pay With");
                        if (null != chooser.resolveActivity(getPackageManager())) {
                            startActivityForResult(chooser, 10);
                        } else {
                            Toast.makeText(SelectPaymentOptions.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                        }*/
                        /*final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Confirming Order", "Please wait...", true);

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

                                final String finalBranchPin = branchPin;
                                databaseReference.child("orders").child(branchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        int numChildren = (int) dataSnapshot.getChildrenCount();

                                        Order order;

                                        String id = "Homy" + (numChildren + 1) + "Bee" + (int) (Math.random() * 100) + address.getName() + "Order" + address.getPincode() + (int) (Math.random() * 100);
                                        if (scheduled.equalsIgnoreCase("yes")) {
                                            order = new Order(customerDetails, itemsOrdered, "COD", time, "yes", "30", "Pending for today", id);
                                        } else {
                                            order = new Order(customerDetails, itemsOrdered, "COD", time, "no", "0", null, id);
                                        }

                                        databaseReference.child("orders").child(finalBranchPin).child(id).setValue(order);

                                        Cursor data = itemDatabaseHelper.getAllData();

                                        myOrdersDatabaseHelper.insertData(data);

                                        Toast.makeText(SelectPaymentOptions.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                                        if (scheduled != null) {
                                            if (scheduled.equalsIgnoreCase("yes")) {
                                                scheduledItemsDatabaseHelper.deleteAllData();
                                                scheduledItemsDatabaseHelper.insertData(data);
                                            }
                                        }

                                        itemDatabaseHelper.deleteAllData();

                                        showNotfication();

                                        databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if (time.equalsIgnoreCase("7AM - 9AM")) {

                                                    String value = dataSnapshot.child("0").getValue(String.class);
                                                    Log.d("value...",value);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("0").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                                else if (time.equalsIgnoreCase("9AM - 11AM")) {

                                                    String value = dataSnapshot.child("1").getValue(String.class);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("1").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                                else if (time.equalsIgnoreCase("11AM - 1PM")) {

                                                    String value = dataSnapshot.child("2").getValue(String.class);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("2").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                                else if (time.equalsIgnoreCase("1PM - 3PM")) {

                                                    String value = dataSnapshot.child("3").getValue(String.class);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("3").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                                else if (time.equalsIgnoreCase("3PM - 5PM")) {

                                                    String value = dataSnapshot.child("4").getValue(String.class);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("4").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                                else if (time.equalsIgnoreCase("5PM - 7PM")) {

                                                    String value = dataSnapshot.child("5").getValue(String.class);
                                                    databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("5").setValue((Integer.valueOf(value)-1)+"");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
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
                        });*/
                    } else if (rb2.isChecked()) {
                        Handler handler = new Handler();
                        final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Loading", "Please wait...", true);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();

                                String amount;

                                if (scheduled.equalsIgnoreCase("yes")) {
                                    amount = String.valueOf(itemDatabaseHelper.getTotalPrice() * 30);
                                } else {
                                    amount = String.valueOf(itemDatabaseHelper.getTotalPrice());
                                }
                                Uri uri =
                                        new Uri.Builder()
                                                .scheme("upi")
                                                .authority("pay")
                                                .appendQueryParameter("pa", "saranshgupta123456789.sg.sg@okhdfcbank")
                                                .appendQueryParameter("pn", "Garvit")
                                                .appendQueryParameter("tn", "Payment to Homy Bee")
                                                .appendQueryParameter("am", amount)
                                                .appendQueryParameter("cu", "INR")
                                                .build();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(uri);

                                Log.d("UPI....","yes1");
                                Intent chooser = Intent.createChooser(intent, "Pay With");
                                if (null != chooser.resolveActivity(getPackageManager())) {
                                    startActivityForResult(chooser, 10);
                                    Log.d("UPI....","yes2");
                                } else {
                                    Toast.makeText(SelectPaymentOptions.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 1500);
                    } else {
                        Toast.makeText(SelectPaymentOptions.this, "Please select a payment option", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SelectPaymentOptions.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
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
            } else {
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
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                final ProgressDialog dialog = ProgressDialog.show(SelectPaymentOptions.this, "Confirming Order", "Please wait...", true);


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

                        final String finalBranchPin = branchPin;
                        databaseReference.child("orders").child(branchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                int numChildren = (int) dataSnapshot.getChildrenCount();

                                String id = "Homy" + (numChildren + 1) + "Bee" + (int) (Math.random() * 100) + address.getName() + "Order" + address.getPincode() + (int) (Math.random() * 100);

                                Order order;
                                if (scheduled.equalsIgnoreCase("yes")) {
                                    order = new Order(customerDetails, itemsOrdered, "COD", time, "yes", "30", "Pending for today", id);
                                } else {
                                    order = new Order(customerDetails, itemsOrdered, "COD", time, "no", "0", null, id);
                                }

                                databaseReference.child("orders").child(finalBranchPin).child(id).setValue(order);

                                Cursor data = itemDatabaseHelper.getAllData();

                                myOrdersDatabaseHelper.insertData(data);
                                Toast.makeText(SelectPaymentOptions.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                                if (scheduled != null) {
                                    if (scheduled.equalsIgnoreCase("yes")) {
                                        scheduledItemsDatabaseHelper.deleteAllData();
                                        scheduledItemsDatabaseHelper.insertData(data);
                                    }
                                }

                                itemDatabaseHelper.deleteAllData();

                                databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (time.equalsIgnoreCase("7AM - 9AM")) {

                                            String value = dataSnapshot.child("0").getValue(String.class);
                                            Log.d("value...",value);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("0").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                        else if (time.equalsIgnoreCase("9AM - 11AM")) {

                                            String value = dataSnapshot.child("1").getValue(String.class);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("1").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                        else if (time.equalsIgnoreCase("11AM - 1PM")) {

                                            String value = dataSnapshot.child("2").getValue(String.class);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("2").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                        else if (time.equalsIgnoreCase("1PM - 3PM")) {

                                            String value = dataSnapshot.child("3").getValue(String.class);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("3").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                        else if (time.equalsIgnoreCase("3PM - 5PM")) {

                                            String value = dataSnapshot.child("4").getValue(String.class);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("4").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                        else if (time.equalsIgnoreCase("5PM - 7PM")) {

                                            String value = dataSnapshot.child("5").getValue(String.class);
                                            databaseReference.child("DeliverGuyAvailable").child(finalBranchPin).child("5").setValue((Integer.valueOf(value)-1)+"");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

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
                showNotfication();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(SelectPaymentOptions.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
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

    public void showNotfication() {
        createNotificationChannel();

        Intent intent = new Intent(SelectPaymentOptions.this, MyOrders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(SelectPaymentOptions.this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(SelectPaymentOptions.this, "company notification")
                .setSmallIcon(R.drawable.homy_bee_main_icon)
                .setContentTitle("Order Confirmed")
                .setContentText("Your order has been placed")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(SelectPaymentOptions.this);
        notificationManager.notify(1, builder.build());

    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "company notification";
            String description = "Include all company notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("company notification", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
