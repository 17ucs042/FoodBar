package com.appsaga.foodbar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EnterDetails extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText phoneNum;
    EditText houseNo;
    EditText apartmentName;
    EditText streetDetails;
    EditText landmark;
    EditText areaDetails;
    EditText pincode;
    EditText nickname;
    ImageButton next;

    String name;
    String phoneNumber;
    String house;
    String area;
    String pincode1;
    String nickname1;

    Button saveAddress;
    Button copyAddress;

    CustomerDatabaseHelper customerDatabaseHelper;
    MyPincodeDatabaseHelper myPincodeDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        customerDatabaseHelper = new CustomerDatabaseHelper(EnterDetails.this);
        myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(EnterDetails.this);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        phoneNum = findViewById(R.id.phone_num);
        houseNo = findViewById(R.id.phone_num);
        houseNo = findViewById(R.id.house_no);
        apartmentName = findViewById(R.id.apartment_name);
        streetDetails = findViewById(R.id.street_details);
        landmark = findViewById(R.id.landmark);
        areaDetails = findViewById(R.id.area_details);
        pincode = findViewById(R.id.pincode);
        nickname = findViewById(R.id.nickname);

        saveAddress = findViewById(R.id.save_address);
        copyAddress = findViewById(R.id.copy_saved_address);

        next = findViewById(R.id.next);

        if(myPincodeDatabaseHelper.getTotalItems()!=0)
        {
            pincode.setText(myPincodeDatabaseHelper.getPincode());
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstName.getText().toString().trim().length()<3)
                {
                    firstName.setError("Name should be of atleast 3 letters");
                    firstName.requestFocus();
                }
                else if(phoneNum.getText().toString().trim().length() != 10)
                {
                    phoneNum.setError("Incorrect Number\nPlease don't add +91");
                    phoneNum.requestFocus();
                }
                else if(houseNo.getText().toString().trim().length()==0)
                {
                    houseNo.setError("House number should not be empty");
                    houseNo.requestFocus();
                }
                else if(areaDetails.getText().toString().trim().length()==0)
                {
                    areaDetails.setError("Area should not be empty");
                    areaDetails.requestFocus();
                }
                else if(pincode.getText().toString().trim().length()!=6)
                {
                    pincode.setError("Incorrect pincode");
                    pincode.requestFocus();
                }
                else if(nickname.getText().toString().trim().length()==0)
                {
                    nickname.setError("Please enter nickname");
                    nickname.requestFocus();
                }
                else if (myPincodeDatabaseHelper.getTotalItems() != 0) {

                    if (!myPincodeDatabaseHelper.getPincode().equalsIgnoreCase(pincode.getText().toString())) {

                        pincode.setError("The pincode you entered doesn't match with the currently set pincode");
                    }
                    else
                    {
                        name = firstName.getText().toString()+" "+lastName.getText().toString();
                        phoneNumber = phoneNum.getText().toString();
                        house = houseNo.getText().toString()+" "+apartmentName.getText().toString()+" "+
                                streetDetails.getText().toString()+" "+landmark.getText().toString();
                        area = areaDetails.getText().toString();
                        pincode1 = pincode.getText().toString();
                        nickname1 = nickname.getText().toString();

                        Address address = new Address(name,phoneNumber,house,area,pincode1,nickname1);

                        Intent intent = new Intent(EnterDetails.this,com.appsaga.foodbar.SelectDeliveryInterval.class);
                        intent.putExtra("address",address);
                        startActivity(intent);

                        finish();
                    }
                }
                else
                {
                    name = firstName.getText().toString()+" "+lastName.getText().toString();
                    phoneNumber = phoneNum.getText().toString();
                    house = houseNo.getText().toString()+" "+apartmentName.getText().toString()+" "+
                            streetDetails.getText().toString()+" "+landmark.getText().toString();
                    area = areaDetails.getText().toString();
                    pincode1 = pincode.getText().toString();
                    nickname1 = nickname.getText().toString();

                    Address address = new Address(name,phoneNumber,house,area,pincode1,nickname1);

                    Intent intent = new Intent(EnterDetails.this,com.appsaga.foodbar.SelectDeliveryInterval.class);
                    intent.putExtra("address",address);
                    startActivity(intent);

                    finish();
                }
            }
        });

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstName.getText().toString().trim().length()<3)
                {
                    firstName.setError("Name should be of atleast 3 letters");
                    firstName.requestFocus();
                }
                else if(phoneNum.getText().toString().trim().length() != 10)
                {
                    phoneNum.setError("Incorrect Number\nPlease don't add +91");
                    phoneNum.requestFocus();
                }
                else if(houseNo.getText().toString().trim().length()==0)
                {
                    houseNo.setError("House number should not be empty");
                    houseNo.requestFocus();
                }
                else if(areaDetails.getText().toString().trim().length()==0)
                {
                    areaDetails.setError("Area should not be empty");
                    areaDetails.requestFocus();
                }
                else if(pincode.getText().toString().trim().length()!=6)
                {
                    pincode.setError("Incorrect pincode");
                    pincode.requestFocus();
                }
                else if(nickname.getText().toString().trim().length()==0)
                {
                    nickname.setError("Please enter nickname");
                    nickname.requestFocus();
                }
                else if (myPincodeDatabaseHelper.getTotalItems() != 0) {

                    if (!myPincodeDatabaseHelper.getPincode().equalsIgnoreCase(pincode.getText().toString())) {

                        pincode.setError("The pincode you entered doesn't match with the currently set pincode");
                    }
                    else
                    {
                        name = firstName.getText().toString() + " " + lastName.getText().toString();
                        phoneNumber = phoneNum.getText().toString();
                        house = houseNo.getText().toString() + " " + apartmentName.getText().toString() + " " +
                                streetDetails.getText().toString() + " " + landmark.getText().toString();
                        area = areaDetails.getText().toString();
                        pincode1 = pincode.getText().toString();
                        nickname1 = nickname.getText().toString();

                        customerDatabaseHelper.insertData(firstName.getText().toString(), lastName.getText().toString(),
                                phoneNumber, houseNo.getText().toString(), apartmentName.getText().toString(), streetDetails.getText().toString(),
                                landmark.getText().toString(), area, pincode1, nickname1);

                        Toast.makeText(EnterDetails.this, "Address saved", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    name = firstName.getText().toString()+" "+lastName.getText().toString();
                    phoneNumber = phoneNum.getText().toString();
                    house = houseNo.getText().toString()+" "+apartmentName.getText().toString()+" "+
                            streetDetails.getText().toString()+" "+landmark.getText().toString();
                    area = areaDetails.getText().toString();
                    pincode1 = pincode.getText().toString();
                    nickname1 = nickname.getText().toString();

                    customerDatabaseHelper.insertData(firstName.getText().toString(),lastName.getText().toString(),
                            phoneNumber,houseNo.getText().toString(),apartmentName.getText().toString(),streetDetails.getText().toString(),
                            landmark.getText().toString(),area,pincode1,nickname1);

                    Toast.makeText(EnterDetails.this,"Address saved",Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor address = customerDatabaseHelper.getAllData();
                address.moveToFirst();

                Log.d("rows...", String.valueOf(customerDatabaseHelper.getTotalItems()));

                if (address.getCount() != 0) {

                    firstName.setText(address.getString(1));
                    lastName.setText(address.getString(2));
                    phoneNum.setText(address.getString(3));
                    houseNo.setText(address.getString(4));
                    apartmentName.setText(address.getString(5));
                    streetDetails.setText(address.getString(6));
                    landmark.setText(address.getString(7));
                    areaDetails.setText(address.getString(8));
                    pincode.setText(address.getString(9));
                    nickname.setText(address.getString(10));
                }
                else
                {
                    Toast.makeText(EnterDetails.this,"No address is saved",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
