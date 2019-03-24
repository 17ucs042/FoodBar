package com.appsaga.foodbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String price1 = getIntent().getStringExtra("price");

        TextView price = findViewById(R.id.price);
        price.setText(price1);
    }
}
