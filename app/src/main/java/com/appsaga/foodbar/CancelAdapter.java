package com.appsaga.foodbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CancelAdapter extends ArrayAdapter<EverydayOrder> {

    DatabaseReference databaseReference;

    public CancelAdapter(Context context, ArrayList<EverydayOrder> orders) {
        super(context, 0,orders);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View cancelView = convertView;

        if(cancelView==null)
        {
            cancelView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cancel_view, parent, false);
        }

        final EverydayOrder currentOrder = getItem(position);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final TextView name = cancelView.findViewById(R.id.name);
        final TextView houseNo = cancelView.findViewById(R.id.house_number);
        final TextView area = cancelView.findViewById(R.id.area);
        final TextView nickname = cancelView.findViewById(R.id.nick_name);
        final TextView phoneNo = cancelView.findViewById(R.id.phone_number);
        final TextView pincode = cancelView.findViewById(R.id.pincode);
        final TextView payType = cancelView.findViewById(R.id.pay_type);
        final TextView status = cancelView.findViewById(R.id.status);
        final TextView daysLeft = cancelView.findViewById(R.id.days_left);
        Button cancelToday = cancelView.findViewById(R.id.cancel_today);

        name.setText("Name: " + currentOrder.getCustomerDetails().get("Name"));
        houseNo.setText("House No.: " + currentOrder.getCustomerDetails().get("House Number"));
        area.setText("Area: " + currentOrder.getCustomerDetails().get("Area"));
        nickname.setText("Type: " + currentOrder.getCustomerDetails().get("Nickname"));
        phoneNo.setText("Phone No.\n" + currentOrder.getCustomerDetails().get("Phone Number"));
        pincode.setText("Pincode\n" + currentOrder.getCustomerDetails().get("Pincode"));
        payType.setText(currentOrder.getPaymentType());
        status.setText("Status: " +currentOrder.getStatus());
        daysLeft.setText("Days Left: "+currentOrder.getDaysLeft());

        Calendar c = Calendar.getInstance();
        int thisDay = c.get(Calendar.DAY_OF_YEAR);
        long todayMillis = c.getTimeInMillis();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        long last = prefs.getLong("date", 0); //If we don't have a saved value, use 0.
        c.setTimeInMillis(last);
        int lastDay = c.get(Calendar.DAY_OF_YEAR);

        cancelToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!currentOrder.getStatus().equalsIgnoreCase("Completed for today")) {

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            databaseReference.child("orders").child(currentOrder.getBranchPin()).child(currentOrder.getId()).child("status").setValue("Cancelled for today");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    status.setText("Status: Cancelled for today");
                } else if (currentOrder.getStatus().equalsIgnoreCase("Completed for today")) {
                    Toast.makeText(getContext(), "Your order has already been completed", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (last == 0 || lastDay != thisDay && thisDay + Calendar.MONTH + Calendar.YEAR > lastDay + Calendar.MONTH + Calendar.YEAR
                && !currentOrder.getStatus().equalsIgnoreCase("Completed for today")
                && !currentOrder.getStatus().equalsIgnoreCase("Cancelled for today")) {
            status.setText("Status: " + "Pending for today");
            SharedPreferences.Editor edit = prefs.edit();
            edit.putLong("date", todayMillis);
            edit.commit();

            databaseReference.child("orders").child(currentOrder.getBranchPin()).child(currentOrder.getId()).child("status").setValue("Pending for today");
        }

        return cancelView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
