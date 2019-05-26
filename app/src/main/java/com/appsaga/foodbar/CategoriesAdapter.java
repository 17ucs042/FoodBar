package com.appsaga.foodbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Categories> {

    public CategoriesAdapter(Context context, ArrayList<Categories> categories) {
        super(context, 0,categories);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.categories_view, parent, false);
        }

        Categories current_category = getItem(position);

        ImageView category_image = listItemView.findViewById(R.id.type_image);
        TextView category_name = listItemView.findViewById(R.id.type);

        category_image.setImageResource(current_category.getImage());
        category_name.setText(current_category.getCategory());

        return listItemView;
    }
}
