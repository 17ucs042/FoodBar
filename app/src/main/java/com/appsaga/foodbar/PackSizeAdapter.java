package com.appsaga.foodbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class PackSizeAdapter extends ArrayAdapter<SelectedItem> {

    ArrayList<SelectedItem> selectedItems;

    public PackSizeAdapter(Context context, ArrayList<SelectedItem> selectedItems) {
        super(context, 0,selectedItems);
        this.selectedItems = selectedItems;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        //ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

        View listItemView = convertView;
        SelectedItem selectedItem = getItem(position);

        if(listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.pack_size_view, parent, false);
        }

        TextView quantity = listItemView.findViewById(R.id.quantity);
        TextView price = listItemView.findViewById(R.id.price);

        quantity.setText(selectedItem.getQuantity());
        price.setText(selectedItem.getPrice());

        return listItemView;
    }
}
