package com.appsaga.foodbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CancelledAddressAdpater extends CursorAdapter {

    public CancelledAddressAdpater(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.select_cancel_address_view, parent, false);
        return v;
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor)
    {
        final TextView tag = view.findViewById(R.id.tag);
        final TextView name = view.findViewById(R.id.name);
        final TextView address = view.findViewById(R.id.address);
        final TextView phone = view.findViewById(R.id.phone);
        RadioButton radioButton = view.findViewById(R.id.radio_button);

        String address1 = cursor.getString(cursor.getColumnIndex("houseNo"));
        String houseNo = address1;

        if(!cursor.getString(cursor.getColumnIndex("apartmentName")).equalsIgnoreCase(""))
        {
            address1 = address1+"\n"+cursor.getString(cursor.getColumnIndex("apartmentName"));
            houseNo = houseNo+"\n"+cursor.getString(cursor.getColumnIndex("apartmentName"));
        }
        if(!cursor.getString(cursor.getColumnIndex("street")).equalsIgnoreCase(""))
        {
            address1 = address1+", "+ cursor.getString(cursor.getColumnIndex("street"));
            houseNo = houseNo+", "+cursor.getString(cursor.getColumnIndex("street"));
        }
        if(!cursor.getString(cursor.getColumnIndex("landmark")).equalsIgnoreCase(""))
        {
            address1 = address1+", "+ cursor.getString(cursor.getColumnIndex("landmark"));
            houseNo=houseNo+", "+cursor.getString(cursor.getColumnIndex("landmark"));
        }

        address1 = address1 +"\n"+ cursor.getString(cursor.getColumnIndex("area"))+" - "+
                cursor.getString(cursor.getColumnIndex("pincode"));

        name.setText(cursor.getString(cursor.getColumnIndex("firstName"))+" "+cursor.getString(cursor.getColumnIndex("lastName")));
        tag.setText(cursor.getString(cursor.getColumnIndex("nickname")));
        address.setText(address1);
        phone.setText("Ph: "+cursor.getString(cursor.getColumnIndex("phoneNum")));

        final String finalHouseNo = houseNo;
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,CancelEverydayOrder.class);
                Address address2 = new Address(name.getText().toString(),phone.getText().toString(), finalHouseNo,cursor.getString(cursor.getColumnIndex("area")),
                        cursor.getString(cursor.getColumnIndex("pincode")),tag.getText().toString());

                intent.putExtra("address",address2);
                context.startActivity(intent);
            }
        });
    }
}
