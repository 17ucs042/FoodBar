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

public class CancelItemsAdapter extends ArrayAdapter<ItemsOrdered> {

    public CancelItemsAdapter(Context context, ArrayList<ItemsOrdered> itemsOrdered) {
        super(context, 0,itemsOrdered);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View cancelItemView = convertView;

        if(cancelItemView==null)
        {
            cancelItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cancel_item_view, parent, false);
        }

        final ItemsOrdered currentItem = getItem(position);

        TextView name = cancelItemView.findViewById(R.id.name);
        TextView quantity = cancelItemView.findViewById(R.id.quantity);
        TextView price = cancelItemView.findViewById(R.id.price);
        TextView itemNum = cancelItemView.findViewById(R.id.itemsNum);

        name.setText(currentItem.getName());
        quantity.setText(currentItem.getQuantity());
        price.setText(currentItem.getPrice());
        itemNum.setText(currentItem.getNumber_of_items());

        return cancelItemView;
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
