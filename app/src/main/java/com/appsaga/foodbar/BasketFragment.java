package com.appsaga.foodbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class BasketFragment extends Fragment {

    View view = null;
    Button startShopping;
    ItemDatabaseHelper itemDatabaseHelper;
    ArrayList<BasketItems> basket;
    ArrayList<String> itemName;
    LinearLayout basketLayout;
    ListView basketList;
    TextView totalPrice;
    RelativeLayout checkoutLayout;
    Button checkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            view = inflater.inflate(R.layout.fragment_basket_fragment, container, false);

            startShopping = view.findViewById(R.id.start_shopping);
            totalPrice = view.findViewById(R.id.total_price);
            checkoutLayout = view.findViewById(R.id.checkout_layout);
            checkout = view.findViewById(R.id.checkout);

            startShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.viewpager, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                    ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                    viewPager.setCurrentItem(0);
                }
            });

            itemDatabaseHelper = new ItemDatabaseHelper(getContext());
            basket = new ArrayList<>();
            itemName = new ArrayList<>();
            basketLayout = view.findViewById(R.id.basket_layout);
            basketList = view.findViewById(R.id.basketList);

            Cursor data = itemDatabaseHelper.getAllData();

        /* (data.moveToNext()) {
            if (data.getInt(1) != 0) {
                BasketItems basketItems = new BasketItems(data.getString(0), data.getString(1),
                        data.getString(2), data.getInt(3));

                basket.add(basketItems);
            }
        }*/

            if (itemDatabaseHelper.getTotalItems() != 0) {

                basketList.setVisibility(View.VISIBLE);
                BaksetItemsAdapter baksetItemsAdapter = new BaksetItemsAdapter(getContext(), data);
                basketList.setAdapter(null);
                basketList.setAdapter(baksetItemsAdapter);
                basketLayout.setVisibility(View.GONE);
            } else {
                basketList.setVisibility(View.GONE);
                basketLayout.setVisibility(View.VISIBLE);
            }

            basketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d("clicked", "Clicked");
                    if (basketList.getCount() == 0) {
                        basketList.setVisibility(View.GONE);
                        basketLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            totalPrice.setText("₹ " + String.valueOf(itemDatabaseHelper.getTotalPrice()));

            if (itemDatabaseHelper.getTotalPrice() == 0) {
                checkoutLayout.setVisibility(View.GONE);
            } else {
                checkoutLayout.setVisibility(View.VISIBLE);
            }

            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //detach();
                    startActivity(new Intent(getContext(), com.appsaga.foodbar.EnterDetails.class));
                }
            });

        return view;
    }

    public void detach()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getFragmentManager().beginTransaction().detach(this).commitNow();
            getFragmentManager().beginTransaction().attach(this).commitNow();
        } else {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor data = itemDatabaseHelper.getAllData();

        if (itemDatabaseHelper.getTotalItems() != 0) {

            basketList.setVisibility(View.VISIBLE);
            BaksetItemsAdapter baksetItemsAdapter = new BaksetItemsAdapter(getContext(), data);
            basketList.setAdapter(null);
            basketList.setAdapter(baksetItemsAdapter);
            basketLayout.setVisibility(View.GONE);
        } else {
            basketList.setVisibility(View.GONE);
            basketLayout.setVisibility(View.VISIBLE);
        }
    }
}
