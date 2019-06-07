package com.appsaga.foodbar;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrdersAdapter extends CursorAdapter {

    ItemDatabaseHelper itemDatabaseHelper;

    public MyOrdersAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        itemDatabaseHelper = new ItemDatabaseHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_orders_view, parent, false);
        return v;
    }

    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {

        final TextView name = view.findViewById(R.id.name);
        final TextView price = view.findViewById(R.id.price);
        final TextView quantity = view.findViewById(R.id.quantity);
        final TextView itemsNum = view.findViewById(R.id.itemsNum);
        ImageView display = view.findViewById(R.id.display);

        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        quantity.setText(cursor.getString(cursor.getColumnIndex("quantity")));
        price.setText(cursor.getString(cursor.getColumnIndex("price")));
        itemsNum.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("itemNum"))));

        final byte[] image = cursor.getBlob(5);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        display.setImageBitmap(bitmap);
    }
}
