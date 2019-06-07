package com.appsaga.foodbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {

    View view;
    ArrayList<Categories> categories = new ArrayList<>();

    ListView categories_list;
    ViewPager viewPager;

    DrawerLayout drawerLayout;
    ImageView navButton;
    ImageView searchIcon;

    MyPincodeDatabaseHelper myPincodeDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null) {

            view = inflater.inflate(R.layout.fragment_categories_fragment, container, false);
            categories_list = view.findViewById(R.id.categories_list);
            viewPager = getActivity().findViewById(R.id.viewpager);
            myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(getContext());

            categories.add(new Categories(R.drawable.fruits_veges,"Fruits & Vegetables"));
            categories.add(new Categories(R.drawable.foodgrain,"Foodgrains, Oils & Masalas"));
            categories.add(new Categories(R.drawable.bakery,"Bakery, Cakes & Dairy"));
            categories.add(new Categories(R.drawable.beverages,"Beverages & Snacks"));
            categories.add(new Categories(R.drawable.kitchen_cleaning,"Cleaning, Kitchen & Baby Care"));
            categories.add(new Categories(R.drawable.beauty_hygiene,"Beauty & Hygiene"));

            CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(),categories);
            categories_list.setAdapter(categoriesAdapter);

            final TextView placeName = getActivity().findViewById(R.id.placeName);
            final Dialog customDialog = new Dialog(getContext());
            customDialog.setContentView(R.layout.custom_dialog);

            final EditText dialogPincode = customDialog.findViewById(R.id.dialog_pincode);
            final Button go = customDialog.findViewById(R.id.button_go);

            categories_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    if (placeName.getText().length() == 6) {

                                TextView textView = view.findViewById(R.id.type);
                                Intent intent = new Intent(getContext(), ShowItems.class);
                                intent.putExtra("value", textView.getText().toString());
                                intent.putExtra("pin", placeName.getText().toString());
                                startActivityForResult(intent, 20);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    TextView textView = view.findViewById(R.id.type);
                                    Intent intent = new Intent(getContext(), ShowItems.class);
                                    intent.putExtra("value", textView.getText().toString());
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });
        }

        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        navButton = view.findViewById(R.id.nav_button);
        searchIcon = view.findViewById(R.id.search_icon);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.START);
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                if(result.equalsIgnoreCase("Home"))
                {
                    viewPager.setCurrentItem(0);
                }
                else if(result.equalsIgnoreCase("Categories"))
                {
                    Log.d("test....","Yese");
                    viewPager.setCurrentItem(1);
                }
                else if(result.equalsIgnoreCase("Search"))
                {
                    viewPager.setCurrentItem(2);
                }
                else if(result.equalsIgnoreCase("Offers"))
                {
                    viewPager.setCurrentItem(3);
                }
                else
                {
                    viewPager.setCurrentItem(4);
                }
            }
        }
    }
}
