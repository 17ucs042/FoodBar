package com.appsaga.foodbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    TextView count;

    public ItemAdapter(Context context, ArrayList<Item> allItems) {
        super(context, 0, allItems);
        itemDatabaseHelper = new ItemDatabaseHelper(context);
    }

    static class ViewHolder {
        private TextView name;
        private TextView price;
        private ImageView display;
        ImageButton add;
        ImageButton subtract;
        Button addButton;
        Button outOfStock;
        LinearLayout addLayout;
        TextView item_num;
        Spinner quantity_spinner;
        byte[][] image;
        TextView type;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        //ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

        View listItemView = convertView;
        final ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.items_view, parent, false);

            holder = new ViewHolder();
            holder.name = listItemView.findViewById(R.id.name);
            holder.price = listItemView.findViewById(R.id.price);
            holder.display = listItemView.findViewById(R.id.display);
            holder.add = listItemView.findViewById(R.id.add);
            holder.subtract = listItemView.findViewById(R.id.subtract);
            holder.addButton = listItemView.findViewById(R.id.add_button);
            holder.outOfStock = listItemView.findViewById(R.id.out_of_stock);
            holder.addLayout = listItemView.findViewById(R.id.add_layout);
            holder.item_num = listItemView.findViewById(R.id.quantity_value);
            holder.quantity_spinner = listItemView.findViewById(R.id.quantity);
            holder.type = listItemView.findViewById(R.id.type);
            holder.image = new byte[1][1];
            listItemView.setTag(holder);

            final Item currentItem = getItem(position);

            HashMap.Entry<String, String> entry1 = currentItem.getQuant_price().entrySet().iterator().next();
            String priceValue = entry1.getValue();

            ArrayList<String> quantities = new ArrayList<>();

            for (HashMap.Entry<String, String> entry : currentItem.getQuant_price().entrySet()) {
                quantities.add(entry.getKey());
            }

            Collections.sort(quantities);

            holder.type.setText(currentItem.getType());
            holder.name.setText(currentItem.getName());
            holder.price.setText(priceValue);
            GlideApp.with(getContext()).load(currentItem.getUrl()).into(holder.display);

        /*while ((display.getDrawable()==null)) {
            dialog = ProgressDialog.show(getContext(), "Loading Images", "Please wait...", true);
        }*/
            ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, quantities);

            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.quantity_spinner.setAdapter(quantityAdapter);
            Log.d("Test.....","NO");

            RelativeLayout relativeLayout = (RelativeLayout) parent.getParent();
            if (relativeLayout.getId() == R.id.show_parent) {

                final TabLayout tabLayout = relativeLayout.findViewById(R.id.tabs);
                final RelativeLayout basketLayout = (RelativeLayout) tabLayout.getTabAt(4).getCustomView();
                count = basketLayout.findViewById(R.id.count);
            } else if (relativeLayout.getId() == R.id.search_parent) {
                FrameLayout frameLayout = ((FrameLayout) relativeLayout.getParent().getParent());
                RelativeLayout relativeLayout1 = (RelativeLayout) frameLayout.getParent().getParent();
                TabLayout tabLayout = relativeLayout1.findViewById(R.id.tabs);
                final RelativeLayout basketLayout = (RelativeLayout) tabLayout.getTabAt(4).getCustomView();
                count = basketLayout.findViewById(R.id.count);
            }
        }
        else {
            Log.d("Test.....","YES");
            holder = (ViewHolder) listItemView.getTag();
        }

        final Item currentItem = getItem(position);

        holder.quantity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String quantity = parent.getItemAtPosition(position).toString();

                for (HashMap.Entry<String, String> entry : currentItem.getQuant_price().entrySet()) {
                    if (entry.getKey().equals(quantity)) {

                        if(entry.getValue().contains("Out of Stock"))
                        {
                            holder.price.setText(entry.getValue().replace("Out of Stock","").trim());
                            holder.outOfStock.setVisibility(View.VISIBLE);
                            holder.addButton.setVisibility(View.INVISIBLE);
                            holder.add.setVisibility(View.GONE);
                            holder.subtract.setVisibility(View.GONE);
                            holder.item_num.setVisibility(View.GONE);
                        }
                        else {
                            holder.price.setText(entry.getValue());
                            holder.outOfStock.setVisibility(View.INVISIBLE);
                            holder.addButton.setVisibility(View.VISIBLE);
                            holder.add.setVisibility(View.VISIBLE);
                            holder.subtract.setVisibility(View.VISIBLE);
                            holder.item_num.setVisibility(View.VISIBLE);
                        }
                    }
                }

                Boolean containsItem = itemDatabaseHelper.containsItem(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString());

                if (containsItem == Boolean.TRUE) {
                    holder.item_num.setText(Integer.toString(itemDatabaseHelper.getItemNum(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString())));
                } else {
                    holder.item_num.setText("0");
                }

                int itemNum = Integer.parseInt(holder.item_num.getText().toString());

                if (itemNum != 0) {
                    holder.addButton.setVisibility(View.GONE);
                    holder.addLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.addButton.setVisibility(View.VISIBLE);
                    holder.addLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Boolean containsItem = itemDatabaseHelper.containsItem(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString());

        if (containsItem == Boolean.TRUE) {
            holder.item_num.setText(Integer.toString(itemDatabaseHelper.getItemNum(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString())));
        } else {
            holder.item_num.setText("0");
        }

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemNum = Integer.parseInt(holder.item_num.getText().toString());

                if (holder.display.getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) holder.display.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    holder.image[0] = stream.toByteArray();
                }

                holder.item_num.setText(Integer.toString(itemNum + 1));

                holder.addButton.setVisibility(View.GONE);
                holder.addLayout.setVisibility(View.VISIBLE);

                itemDatabaseHelper.insertData(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString(),
                        holder.price.getText().toString(), itemNum + 1, holder.image[0], currentItem.getType());

                itemDatabaseHelper.update(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString(),
                        holder.price.getText().toString(), itemNum + 1, holder.image[0], currentItem.getType());

                //Cursor data = itemDatabaseHelper.getAllData();
                count.setVisibility(View.VISIBLE);
                count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemNum = Integer.parseInt(holder.item_num.getText().toString());

                if (holder.display.getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) holder.display.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    holder.image[0] = stream.toByteArray();
                }

                if (itemNum < 12) {
                    holder.item_num.setText(Integer.toString(itemNum + 1));

                    itemDatabaseHelper.update(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString(),
                            holder.price.getText().toString(), itemNum + 1, holder.image[0], currentItem.getType());
                }

                count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
            }
        });

        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemNum = Integer.parseInt(holder.item_num.getText().toString());

                if (holder.display.getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) holder.display.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    holder.image[0] = stream.toByteArray();
                }

                if (itemNum > 1) {
                    holder.item_num.setText(Integer.toString(itemNum - 1));

                    itemDatabaseHelper.update(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString(),
                            holder.price.getText().toString(), itemNum - 1, holder.image[0], currentItem.getType());

                }
                if (itemNum == 1) {
                    holder.item_num.setText(Integer.toString(itemNum - 1));

                    itemDatabaseHelper.update(currentItem.getName(), holder.quantity_spinner.getSelectedItem().toString(),
                            holder.price.getText().toString(), itemNum - 1, holder.image[0], currentItem.getType());

                    holder.addButton.setVisibility(View.VISIBLE);
                    holder.addLayout.setVisibility(View.GONE);

                    count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
                }

                if (itemDatabaseHelper.getTotalItems() == 0) {
                    count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
                    count.setVisibility(View.GONE);
                }
            }
        });

        return listItemView;
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
