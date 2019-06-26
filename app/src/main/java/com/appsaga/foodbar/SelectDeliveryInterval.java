package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SelectDeliveryInterval extends AppCompatActivity {

    TextView completeAddress;
    RadioButton [] radioButtons = new RadioButton[6];
    RelativeLayout [] relativeLayouts = new RelativeLayout[6];
    TextView [] textViews = new TextView[6];
    Button proceedToPay;
    String time;
    Button scheduleEveryday;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_interval);

        scheduleEveryday = findViewById(R.id.schedule_everyday);

        final Address address = (Address) getIntent().getSerializableExtra("address");

        completeAddress = findViewById(R.id.complete_address);
        completeAddress.setText(address.getHouseNo()+", "+address.getArea()+",\nPin - "+address.getPincode());

        radioButtons[0] = findViewById(R.id.am7);
        radioButtons[1] = findViewById(R.id.am9);
        radioButtons[2] = findViewById(R.id.am11);
        radioButtons[3] = findViewById(R.id.pm1);
        radioButtons[4] = findViewById(R.id.pm3);
        radioButtons[5] = findViewById(R.id.pm5);

        relativeLayouts[0] = findViewById(R.id.radio1);
        relativeLayouts[1] = findViewById(R.id.radio2);
        relativeLayouts[2] = findViewById(R.id.radio3);
        relativeLayouts[3] = findViewById(R.id.radio4);
        relativeLayouts[4] = findViewById(R.id.radio5);
        relativeLayouts[5] = findViewById(R.id.radio6);

        textViews[0] = findViewById(R.id.am7_available);
        textViews[1] = findViewById(R.id.am9_available);
        textViews[2] = findViewById(R.id.am11_available);
        textViews[3] = findViewById(R.id.pm1_available);
        textViews[4] = findViewById(R.id.pm3_available);
        textViews[5] = findViewById(R.id.pm5_available);

        final ProgressDialog progressDialog = ProgressDialog.show(SelectDeliveryInterval.this, "Loading", "Please Wait...", true);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Delivers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String deliveryPincode=null;

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    HashMap<String,String> hashMap = (HashMap<String, String>)ds.getValue();
                    HashMap.Entry<String,String> entry = hashMap.entrySet().iterator().next();

                    if(entry.getValue().equalsIgnoreCase(address.getPincode()))
                    {
                        deliveryPincode = entry.getKey();
                    }
                }

                if(deliveryPincode!=null)
                {
                    databaseReference.child("DeliverGuyAvailable").child(deliveryPincode).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ArrayList<String> arrayList = (ArrayList<String>)dataSnapshot.getValue();

                            for (int i=0;i<6;i++)
                            {
                                if(arrayList.get(i).equalsIgnoreCase("0"))
                                {
                                    textViews[i].setVisibility(View.VISIBLE);
                                    radioButtons[i].setEnabled(Boolean.FALSE);
                                    relativeLayouts[i].setEnabled(Boolean.FALSE);
                                }
                            }

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        radioButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[1].setChecked(false);
                radioButtons[2].setChecked(false);
                radioButtons[3].setChecked(false);
                radioButtons[4].setChecked(false);
                radioButtons[5].setChecked(false);
            }
        });

        radioButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[0].setChecked(false);
                radioButtons[5].setChecked(false);
                radioButtons[2].setChecked(false);
                radioButtons[3].setChecked(false);
                radioButtons[4].setChecked(false);
            }
        });

        radioButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[1].setChecked(false);
                radioButtons[0].setChecked(false);
                radioButtons[3].setChecked(false);
                radioButtons[4].setChecked(false);
                radioButtons[5].setChecked(false);
            }
        });

        radioButtons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[1].setChecked(false);
                radioButtons[2].setChecked(false);
                radioButtons[0].setChecked(false);
                radioButtons[4].setChecked(false);
                radioButtons[5].setChecked(false);
            }
        });

        radioButtons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[1].setChecked(false);
                radioButtons[2].setChecked(false);
                radioButtons[3].setChecked(false);
                radioButtons[0].setChecked(false);
                radioButtons[5].setChecked(false);
            }
        });

        radioButtons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtons[1].setChecked(false);
                radioButtons[2].setChecked(false);
                radioButtons[3].setChecked(false);
                radioButtons[4].setChecked(false);
                radioButtons[0].setChecked(false);
            }
        });

        proceedToPay = findViewById(R.id.proceed_pay);

        proceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int t=1;

                if(radioButtons[0].isChecked())
                {
                    time="7AM - 9AM";
                }
                else if(radioButtons[1].isChecked())
                {
                    time="9AM - 11AM";
                }
                else if(radioButtons[2].isChecked())
                {
                    time="11AM - 1PM";
                }
                else if(radioButtons[3].isChecked())
                {
                    time="1PM - 3PM";
                }
                else if(radioButtons[4].isChecked())
                {
                    time="3PM - 5PM";
                }
                else if(radioButtons[5].isChecked())
                {
                    time = "5PM - 7PM";
                }
                else
                {
                    Toast.makeText(SelectDeliveryInterval.this,"Please select delivery time",Toast.LENGTH_LONG).show();
                    t=0;
                }

                if(t==1)
                {
                    Intent intent = new Intent(SelectDeliveryInterval.this,SelectPaymentOptions.class);
                    intent.putExtra("time",time);
                    intent.putExtra("address",address);
                    intent.putExtra("scheduledEveryday","no");

                    startActivity(intent);
                    finish();
                }
            }
        });

        scheduleEveryday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectDeliveryInterval.this,ScheduleEveryday.class);
                intent.putExtra("time",time);
                intent.putExtra("address",address);
                startActivity(intent);
                finish();
            }
        });
    }
}
