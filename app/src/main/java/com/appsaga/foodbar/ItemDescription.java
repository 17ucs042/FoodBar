package com.appsaga.foodbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class ItemDescription extends AppCompatActivity {

    TextView name;
    TextView quantity;
    TextView price;
    ImageView image;
    ImageView search;
    ImageView share;
    LinearLayout shareLayout;
    TextView count;
    ItemDatabaseHelper itemDatabaseHelper;
    LinearLayout addLayout;
    LinearLayout itemNumLayout;
    TextView itemNum;
    ImageButton add;
    ImageButton subtract;
    RelativeLayout basketLayout;
    ImageView backNavigate;

    String name1;
    String quantity1;
    byte[] byteArray;

    Item item;
    ArrayList<SelectedItem> selectedItems;
    ListView packList;
    PackSizeAdapter packSizeAdapter;

    static ProgressDialog dialog;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        name1 = getIntent().getStringExtra("name");
        quantity1 = getIntent().getStringExtra("quantity");
        item = (Item) getIntent().getSerializableExtra("item");
        final String price1 = getIntent().getStringExtra("price");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        itemDatabaseHelper = new ItemDatabaseHelper(ItemDescription.this);
        selectedItems = new ArrayList<>();

        name = findViewById(R.id.name);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        share = findViewById(R.id.share);
        shareLayout = findViewById(R.id.share_layout);
        image = findViewById(R.id.des_image);
        count = findViewById(R.id.count);
        addLayout = findViewById(R.id.add_layout);
        itemNumLayout = findViewById(R.id.itemNumLayout);
        itemNum = findViewById(R.id.itemsNum);
        add = findViewById(R.id.add_item);
        subtract = findViewById(R.id.subtract_item);
        packList = findViewById(R.id.pack_list);
        basketLayout = findViewById(R.id.basket_layout);
        search = findViewById(R.id.search);
        backNavigate = findViewById(R.id.back_navigate);

        name.setText(item.getName() + ",");
        quantity.setText(quantity1);
        price.setText(price1);
        image.setImageBitmap(bitmap);
        image.setTag("Path");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byteArray = stream.toByteArray();
        final Uri uri = Uri.parse(item.getUrl());

        for (HashMap.Entry<String, String> entry : item.getQuant_price().entrySet()) {
            selectedItems.add(new SelectedItem(entry.getValue(), entry.getKey(), "In Stock"));
        }

        packList = findViewById(R.id.pack_list);
        Collections.sort(selectedItems);
        packSizeAdapter = new PackSizeAdapter(ItemDescription.this, selectedItems);
        packList.setAdapter(packSizeAdapter);

        packList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String price1 = ((TextView)view.findViewById(R.id.price)).getText().toString();
                String quantity1 = ((TextView)view.findViewById(R.id.quantity)).getText().toString();

                price.setText(price1);
                quantity.setText(quantity1);

                if(itemDatabaseHelper.getItemNum(name1,quantity1)!=0)
                {
                    addLayout.setVisibility(View.GONE);
                    itemNumLayout.setVisibility(View.VISIBLE);
                    itemNum.setText(String.valueOf(itemDatabaseHelper.getItemNum(name1,quantity1)));
                }
                else
                {
                    addLayout.setVisibility(View.VISIBLE);
                    itemNumLayout.setVisibility(View.GONE);
                }
            }
        });

        if (itemDatabaseHelper.getTotalItems() != 0) {
            count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
            count.setVisibility(View.VISIBLE);
        } else {
            count.setVisibility(View.GONE);
        }

        if (itemDatabaseHelper.containsItem(name1, quantity.getText().toString())) {
            addLayout.setVisibility(View.GONE);
            itemNumLayout.setVisibility(View.VISIBLE);
            itemNum.setText(String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity.getText().toString())));
        } else {
            addLayout.setVisibility(View.VISIBLE);
            itemNumLayout.setVisibility(View.GONE);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ItemDescription.this,"Just a sec...",Toast.LENGTH_SHORT).show();
                shareImage(item.getUrl(),ItemDescription.this);
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ItemDescription.this,"Just a sec...",Toast.LENGTH_SHORT).show();
                shareImage(item.getUrl(),ItemDescription.this);
            }
        });

        basketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ItemDescription.this,HomeScreen.class);
                intent.putExtra("intent","Basket");
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ItemDescription.this,HomeScreen.class);
                intent.putExtra("intent","Search");
                startActivity(intent);
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count.setVisibility(View.VISIBLE);
                String item_num = String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity.getText().toString()) + 1);
                itemNum.setText(item_num);
                addLayout.setVisibility(View.GONE);
                itemNumLayout.setVisibility(View.VISIBLE);
                itemDatabaseHelper.insertData(name1, quantity.getText().toString(), price.getText().toString(), Integer.parseInt(item_num), byteArray, item.getType());
                itemDatabaseHelper.update(name1, quantity.getText().toString(), price.getText().toString(), Integer.parseInt(item_num), byteArray, item.getType());
                count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemDatabaseHelper.getItemNum(name1, quantity.getText().toString()) < 12) {
                    String item_num = String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity.getText().toString()) + 1);
                    itemDatabaseHelper.update(name1, quantity.getText().toString(), price.getText().toString(), Integer.parseInt(item_num), byteArray, item.getType());
                    itemNum.setText(item_num);
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemDatabaseHelper.getItemNum(name1, quantity.getText().toString()) > 1) {
                    String item_num = String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity1) - 1);
                    itemDatabaseHelper.update(name1, quantity.getText().toString(), price.getText().toString(), Integer.parseInt(item_num), byteArray, item.getType());
                    itemNum.setText(item_num);
                } else if (itemDatabaseHelper.getItemNum(name1, quantity.getText().toString()) == 1) {
                    String item_num = String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity1) - 1);
                    itemDatabaseHelper.update(name1, quantity.getText().toString(), price.getText().toString(), Integer.parseInt(item_num), byteArray, item.getType());
                    itemNum.setText(item_num);
                    addLayout.setVisibility(View.VISIBLE);
                    itemNumLayout.setVisibility(View.GONE);

                    if (itemDatabaseHelper.getTotalItems() != 0) {
                        count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
                    }
                    else
                    {
                        count.setText(String.valueOf(itemDatabaseHelper.getTotalItems()));
                        count.setVisibility(View.GONE);
                    }
                }
            }
        });

        backNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

  //      int position = selectedItems.indexOf(new SelectedItem(price.getText().toString(),quantity.getText().toString(),"In Stock"));
//        packList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.primary_light));

        if (itemDatabaseHelper.containsItem(name1, quantity.getText().toString())) {
            addLayout.setVisibility(View.GONE);
            itemNumLayout.setVisibility(View.VISIBLE);
            itemNum.setText(String.valueOf(itemDatabaseHelper.getItemNum(name1, quantity.getText().toString())));
        } else {
            addLayout.setVisibility(View.VISIBLE);
            itemNumLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    static public void shareImage(String url, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT, "I found this item on Homy Bee");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent chooserIntent = Intent.createChooser(i, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(chooserIntent);
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
