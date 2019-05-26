package com.appsaga.foodbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.signature.ObjectKey;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Handler;

public class ItemAdapter extends ArrayAdapter<Item> {

    ItemDatabaseHelper itemDatabaseHelper;
    private Activity activityContext;

    public ItemAdapter(Context context, ArrayList<Item> allItems,Activity activityContext) {
        super(context, 0, allItems);
        itemDatabaseHelper = new ItemDatabaseHelper(context);
        this.activityContext = activityContext;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_view, parent, false);
        }

        final Item currentItem = getItem(position);

        HashMap.Entry<String, String> entry1 = currentItem.getQuant_price().entrySet().iterator().next();
        String priceValue = entry1.getValue();

        ArrayList<String> quantities = new ArrayList<>();

        for (HashMap.Entry<String, String> entry : currentItem.getQuant_price().entrySet()) {
            quantities.add(entry.getKey());
        }

        Collections.sort(quantities);

        TextView name = listItemView.findViewById(R.id.name);
        name.setText(currentItem.getName());

        final TextView price = listItemView.findViewById(R.id.price);
        price.setText(priceValue);

        final ImageView display = listItemView.findViewById(R.id.display);
        Picasso.with(getContext()).load(currentItem.getUrl()).into(display);

        ImageButton add = listItemView.findViewById(R.id.add);
        ImageButton subtract = listItemView.findViewById(R.id.subtract);
        final TextView item_num = listItemView.findViewById(R.id.quantity_value);

        ProgressDialog dialog = null;
        final byte[][] image = new byte[1][1];

        /*while ((display.getDrawable()==null)) {
            dialog = ProgressDialog.show(getContext(), "Loading Images", "Please wait...", true);
        }*/

        final Spinner quantity_spinner = listItemView.findViewById(R.id.quantity);
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, quantities);

        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantity_spinner.setAdapter(quantityAdapter);

        quantity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String quantity = parent.getItemAtPosition(position).toString();

                for (HashMap.Entry<String, String> entry : currentItem.getQuant_price().entrySet()) {
                    if (entry.getKey().equals(quantity)) {
                        price.setText(entry.getValue());
                    }
                }

                Boolean containsItem = itemDatabaseHelper.containsItem(currentItem.getName(), quantity_spinner.getSelectedItem().toString());

                if (containsItem == Boolean.TRUE) {
                    item_num.setText(Integer.toString(itemDatabaseHelper.getItemNum(currentItem.getName(), quantity_spinner.getSelectedItem().toString())));
                } else {
                    item_num.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Boolean containsItem = itemDatabaseHelper.containsItem(currentItem.getName(), quantity_spinner.getSelectedItem().toString());

        if (containsItem == Boolean.TRUE) {
            item_num.setText(Integer.toString(itemDatabaseHelper.getItemNum(currentItem.getName(), quantity_spinner.getSelectedItem().toString())));
        } else {
            item_num.setText("0");
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemNum = Integer.parseInt(item_num.getText().toString());

                if(display.getDrawable()!=null)
                {
                    BitmapDrawable drawable = (BitmapDrawable) display.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image[0] = stream.toByteArray();
                }

                if (itemNum < 12) {
                    item_num.setText(Integer.toString(itemNum + 1));

                    if (itemNum == 0) {
                        itemDatabaseHelper.insertData(currentItem.getName(), quantity_spinner.getSelectedItem().toString(),
                                price.getText().toString(), itemNum + 1, image[0]);

                        itemDatabaseHelper.update(currentItem.getName(), quantity_spinner.getSelectedItem().toString(),
                                price.getText().toString(), itemNum + 1, image[0]);

                    } else {
                        itemDatabaseHelper.update(currentItem.getName(), quantity_spinner.getSelectedItem().toString(),
                                price.getText().toString(), itemNum + 1, image[0]);

                    }
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemNum = Integer.parseInt(item_num.getText().toString());

                if(display.getDrawable()!=null)
                {
                    BitmapDrawable drawable = (BitmapDrawable) display.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image[0] = stream.toByteArray();
                }

                if (itemNum > 0) {
                    item_num.setText(Integer.toString(itemNum - 1));

                    itemDatabaseHelper.update(currentItem.getName(), quantity_spinner.getSelectedItem().toString(),
                            price.getText().toString(), itemNum - 1, image[0]);
                }

            }
        });

        return listItemView;
    }
}
