package com.appsaga.foodbar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CategoryAllAdapter extends ArrayAdapter<Items> {

    public CategoryAllAdapter(Context context, ArrayList<Items> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_view, parent, false);
        }

        TextView name = listItemView.findViewById(R.id.name);
        Button notInStock = listItemView.findViewById(R.id.not_in_stock);
        ImageButton add = listItemView.findViewById(R.id.add);
        ImageButton subtract = listItemView.findViewById(R.id.subtract);
        TextView price = listItemView.findViewById(R.id.price);

        final Items currentItem = getItem(position);

        if (currentItem.getQuantity() != 0) {

            name.setText(currentItem.getName());

            price.setText(currentItem.getPrice());

            if (currentItem.inStock == Boolean.FALSE) {
                notInStock.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
                subtract.setVisibility(View.GONE);
            } else {
                notInStock.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
                subtract.setVisibility(View.VISIBLE);
            }

            final TextView quantity_value = listItemView.findViewById(R.id.quantity_value);
            quantity_value.setText(String.valueOf(currentItem.getQuantity()));

        }
        return listItemView;
    }
}
