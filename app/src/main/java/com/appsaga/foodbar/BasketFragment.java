package com.appsaga.foodbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;

public class BasketFragment extends Fragment {

    View view;
    Button startShopping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basket_fragment, container, false);

        startShopping = view.findViewById(R.id.start_shopping);

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

        return view;
    }
}
