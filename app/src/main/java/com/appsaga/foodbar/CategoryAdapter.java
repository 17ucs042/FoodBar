package com.appsaga.foodbar;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

class CategoryAdapter extends ArrayAdapter<Items> {

    DatabaseHelper mDatabaseHelper;

    public CategoryAdapter(Context context, ArrayList<Items> items) {
        super(context, 0, items);
        mDatabaseHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_view, parent, false);
        }

        final Items currentItem = getItem(position);

        TextView name = listItemView.findViewById(R.id.name);

        name.setText(currentItem.getName());

        TextView price = listItemView.findViewById(R.id.price);
        price.setText(currentItem.getPrice());

        Button notInStock = listItemView.findViewById(R.id.not_in_stock);
        ImageButton add = listItemView.findViewById(R.id.add);
        ImageButton subtract = listItemView.findViewById(R.id.subtract);

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
        quantity_value.setText(String.valueOf(mDatabaseHelper.getQuantity(currentItem.getName())));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentItem.inStock == Boolean.TRUE) {

                    quantity_value.setText(String.valueOf(mDatabaseHelper.getQuantity(currentItem.getName()) + 1));
                    mDatabaseHelper.update(currentItem.getName(), mDatabaseHelper.getQuantity(currentItem.getName()) + 1, mDatabaseHelper.getPrice(currentItem.getName()) + 1);

                    //currentItem.setQuantity(currentItem.getQuantity()+1);
                    //Toast.makeText(getContext(), "Item at position " + position + " was clicked", Toast.LENGTH_SHORT).show();

                   /* if(currentItem.getQuantity()>0)
                    {
                        *//*Intent intent = new Intent("item sent");
                        intent.putExtra("Item", currentItem);
                        getContext().sendBroadcast(intent);*//*
                    }
                    if (mDatabaseHelper.getQuantity(currentItem.getName()) == 0) {
                        boolean isInserted = mDatabaseHelper.insertData(currentItem.getName(), currentItem.getQuantity()
                                , currentItem.getPrice());
                        if (isInserted)
                            Toast.makeText(getContext(), "inserted", Toast.LENGTH_SHORT).show();

                    }
                    if (currentItem.getQuantity() >= 0) {
                        boolean isUpdated = mDatabaseHelper.update(currentItem.getName(), currentItem.getQuantity(), currentItem.getPrice());
                        if (isUpdated) {
                            Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    /*if(currentItem.getQuantity()==0){
                        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                        db.delete("OrderTable","item=?",new String[]{currentItem.getName()});
                    }*/
                }

                ((ListView) parent).performItemClick(v, position, 0);
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentItem.inStock == Boolean.TRUE && currentItem.getQuantity() > 0) {

                    quantity_value.setText(String.valueOf(currentItem.getQuantity() - 1));
                    currentItem.setQuantity(currentItem.getQuantity() - 1);
                    //Toast.makeText(getContext(), "Item at position " + position + " was clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Find the ImageView in the list_item.xml layout with the ID image.
        /*ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Check if an image is provided for this word or not
        if (currentWord.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentWord.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }*/

        // Set the theme color for the list item
        /*View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);*/

        return listItemView;
    }
}
