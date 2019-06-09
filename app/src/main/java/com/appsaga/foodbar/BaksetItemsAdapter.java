package com.appsaga.foodbar;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BaksetItemsAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    ItemDatabaseHelper itemDatabaseHelper;

    public BaksetItemsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mLayoutInflater = LayoutInflater.from(context);
        itemDatabaseHelper = new ItemDatabaseHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false);
        return v;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {
        // Find fields to populate in inflated template
        final TextView name = view.findViewById(R.id.name);
        final TextView price = view.findViewById(R.id.price);
        final TextView quantity = view.findViewById(R.id.quantity);
        final TextView itemsNum = view.findViewById(R.id.itemsNum);
        ImageView display = view.findViewById(R.id.display);

        ImageButton add = view.findViewById(R.id.add);
        ImageButton subtract = view.findViewById(R.id.subtract);

        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        quantity.setText(cursor.getString(cursor.getColumnIndex("quantity")));
        price.setText(cursor.getString(cursor.getColumnIndex("price")));
        itemsNum.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("itemNum"))));

        final byte[] image = cursor.getBlob(5);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        display.setImageBitmap(bitmap);

        final int itemId = cursor.getInt(cursor.getColumnIndex("_id"));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = Integer.parseInt(itemsNum.getText().toString());

                if (num < 12) {
                    itemDatabaseHelper.update(name.getText().toString(), quantity.getText().toString(), price.getText().toString(), num + 1, image,cursor.getString(cursor.getColumnIndex("type")));
                    itemsNum.setText(Integer.toString(num + 1));

                    TextView textView = ((RelativeLayout) view.getParent().getParent()).findViewById(R.id.total_price);
                    textView.setText(String.valueOf(itemDatabaseHelper.getTotalPrice()) + " ₹ ");

                    ((ListView) view.getParent()).setVisibility(View.VISIBLE);
                }
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = Integer.parseInt(itemsNum.getText().toString());

                if (num > 0) {
                    itemDatabaseHelper.update(name.getText().toString(), quantity.getText().toString(), price.getText().toString(), num - 1, image,cursor.getString(cursor.getColumnIndex("type")));
                    itemsNum.setText(Integer.toString(num - 1));
                }

                if ((num - 1) == 0) {
                    itemDatabaseHelper.deleteData(itemId);
                    Cursor newCursor = itemDatabaseHelper.getAllData();
                    changeCursor(newCursor);
                    notifyDataSetChanged();

                    if (((ListView) view.getParent()).getCount() == 0) {
                        ((ListView) view.getParent()).setVisibility(View.GONE);

                        ((RelativeLayout) view.getParent().getParent()).findViewById(R.id.basket_layout).setVisibility(View.VISIBLE);
                    }
                }

                TextView textView = ((RelativeLayout) view.getParent().getParent()).findViewById(R.id.total_price);
                textView.setText(String.valueOf(itemDatabaseHelper.getTotalPrice()) + "₹ ");

                if (itemDatabaseHelper.getTotalItems() == 0) {
                    RelativeLayout checkoutLayout = ((RelativeLayout) view.getParent().getParent()).findViewById(R.id.checkout_layout);
                    checkoutLayout.setVisibility(View.GONE);
                }
            }
        });
    }
}
