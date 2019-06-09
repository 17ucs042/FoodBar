package com.appsaga.foodbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

        if(position==0)
        {
            listItemView.setSelected(true);
        }

        TextView quantity = listItemView.findViewById(R.id.quantity);
        TextView price = listItemView.findViewById(R.id.price);
        TextView inStock = listItemView.findViewById(R.id.inStock);

        quantity.setText(selectedItem.getQuantity());

        if(selectedItem.getPrice().contains("Out of Stock"))
        {
            price.setText(selectedItem.getPrice().replace("Out of Stock","").trim());
            inStock.setText("Out of Stock");
        }
        else
        {
            price.setText(selectedItem.getPrice());
            inStock.setText("In Stock");
        }

        return listItemView;
    }
}
