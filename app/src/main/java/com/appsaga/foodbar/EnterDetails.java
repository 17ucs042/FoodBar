package com.appsaga.foodbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    TextView workTag;
    TextView homeTag;
    TextView otherTag;
    TextView tagAs;
    ImageButton next;

    String name;
    String phoneNumber;
    String house;
    String area;
    String pincode1;
    String nickname1;

    Button saveAddress;

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
        workTag = findViewById(R.id.work_tag);
        homeTag = findViewById(R.id.home_tag);
        otherTag = findViewById(R.id.other_tag);
        tagAs=findViewById(R.id.tag);

        saveAddress = findViewById(R.id.save_address);

        if(myPincodeDatabaseHelper.getTotalItems()!=0)
        {
            pincode.setText(myPincodeDatabaseHelper.getPincode());
        }

        workTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workTag.setTextColor(Color.parseColor("#FFC107"));
                workTag.setSelected(true);
                homeTag.setSelected(false);
                homeTag.setTextColor(Color.parseColor("#000000"));
                otherTag.setSelected(false);
                otherTag.setTextColor(Color.parseColor("#000000"));
            }
        });

        homeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeTag.setTextColor(Color.parseColor("#FFC107"));
                homeTag.setSelected(true);
                workTag.setSelected(false);
                workTag.setTextColor(Color.parseColor("#000000"));
                otherTag.setSelected(false);
                otherTag.setTextColor(Color.parseColor("#000000"));
            }
        });

        otherTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otherTag.setTextColor(Color.parseColor("#FFC107"));
                otherTag.setSelected(true);
                workTag.setSelected(false);
                workTag.setTextColor(Color.parseColor("#000000"));
                homeTag.setSelected(false);
                homeTag.setTextColor(Color.parseColor("#000000"));
            }
        });

        /*next.setOnClickListener(new View.OnClickListener() {
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
                else if(!(otherTag.isSelected()||homeTag.isSelected()||workTag.isSelected()))
                {
                    tagAs.setError("Please select a tag");
                    tagAs.requestFocus();
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
                        //nickname1 = nickname.getText().toString();

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
                   // nickname1 = nickname.getText().toString();

                    Address address = new Address(name,phoneNumber,house,area,pincode1,nickname1);

                    Intent intent = new Intent(EnterDetails.this,com.appsaga.foodbar.SelectDeliveryInterval.class);
                    intent.putExtra("address",address);
                    startActivity(intent);

                    finish();
                }
            }
        });*/

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
                else if(!(otherTag.isSelected()||homeTag.isSelected()||workTag.isSelected()))
                {
                    tagAs.setError("Please select a tag");
                    tagAs.requestFocus();
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

                        if(workTag.isSelected())
                        {
                            nickname1= workTag.getText().toString();
                        }
                        else if(homeTag.isSelected())
                        {
                            nickname1= homeTag.getText().toString();
                        }
                        else {
                            nickname1= otherTag.getText().toString();
                        }

                        customerDatabaseHelper.insert(firstName.getText().toString(), lastName.getText().toString(),
                                phoneNumber, houseNo.getText().toString(), apartmentName.getText().toString(), streetDetails.getText().toString(),
                                landmark.getText().toString(), area, pincode1, nickname1);

                        Toast.makeText(EnterDetails.this, "Address saved", Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent(EnterDetails.this,SavedAddresses.class);
                        startActivity(returnIntent);
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

                    if(workTag.isSelected())
                    {
                        nickname1= workTag.getText().toString();
                    }
                    else if(homeTag.isSelected())
                    {
                        nickname1= homeTag.getText().toString();
                    }
                    else {
                        nickname1= otherTag.getText().toString();
                    }

                    customerDatabaseHelper.insert(firstName.getText().toString(),lastName.getText().toString(),
                            phoneNumber,houseNo.getText().toString(),apartmentName.getText().toString(),streetDetails.getText().toString(),
                            landmark.getText().toString(),area,pincode1,nickname1);

                    Toast.makeText(EnterDetails.this,"Address saved",Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent(EnterDetails.this,SavedAddresses.class);
                    startActivity(returnIntent);
                    finish();
                }
            }
        });

        /*copyAddress.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
