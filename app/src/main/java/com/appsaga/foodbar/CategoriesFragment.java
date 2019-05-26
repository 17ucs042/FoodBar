package com.appsaga.foodbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {

    View view;
    ArrayList<Categories> categories = new ArrayList<>();

    ListView categories_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null) {

            view = inflater.inflate(R.layout.fragment_categories_fragment, container, false);
            categories_list = view.findViewById(R.id.categories_list);

            categories.add(new Categories(R.drawable.fruits_veges,"Fruits & Vegetables"));
            categories.add(new Categories(R.drawable.foodgrain,"Foodgrains, Oils & Masalas"));
            categories.add(new Categories(R.drawable.bakery,"Bakery, Cakes & Dairy"));
            categories.add(new Categories(R.drawable.beverages,"Beverages & Snacks"));
            categories.add(new Categories(R.drawable.kitchen_cleaning,"Cleaning, Kitchen & Baby Care"));
            categories.add(new Categories(R.drawable.beauty_hygiene,"Beauty & Hygiene"));

            CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(),categories);
            categories_list.setAdapter(categoriesAdapter);

            categories_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    TextView textView = view.findViewById(R.id.type);
                    Intent intent = new Intent(getContext(),ShowItems.class);
                    intent.putExtra("value",textView.getText().toString());
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}
