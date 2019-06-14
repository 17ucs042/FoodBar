package com.appsaga.foodbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDeliveryInterval extends AppCompatActivity {

    TextView completeAddress;
    RadioButton [] radioButtons = new RadioButton[6];
    Button proceedToPay;
    String time;
    Button scheduleEveryday;

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
