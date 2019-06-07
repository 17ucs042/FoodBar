package com.appsaga.foodbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterPincode extends AppCompatActivity {

    MyPincodeDatabaseHelper myPincodeDatabaseHelper;
    ItemDatabaseHelper itemDatabaseHelper;
    EditText newPin;
    Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pincode);

        myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(EnterPincode.this);
        itemDatabaseHelper = new ItemDatabaseHelper(EnterPincode.this);

        newPin = findViewById(R.id.new_pin);
        Continue = findViewById(R.id.button_continue);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newPin.getText().toString().length()!=6)
                {
                    newPin.setError("Invalid Pincode");
                }
                else {
                    final Dialog customDialog2 = new Dialog(EnterPincode.this);
                    customDialog2.setContentView(R.layout.custom_dialog2);

                    Button contAnyway = customDialog2.findViewById(R.id.cont_anyway);
                    customDialog2.show();

                    contAnyway.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (newPin.getText().toString().length() == 6) {
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", newPin.getText().toString());
                                setResult(Activity.RESULT_OK, returnIntent);
                                myPincodeDatabaseHelper.insertData(newPin.getText().toString());
                                itemDatabaseHelper.deleteAllData();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
