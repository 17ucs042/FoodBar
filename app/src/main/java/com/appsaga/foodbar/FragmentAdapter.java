package com.appsaga.foodbar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new HomeFragment();
        }
        else if (position == 1) {
            return new CategoriesFragment();
        } else if (position == 2) {
            return new SearchFragment();
        } else if(position == 3)
            return new OfferFragment();
        else
        {
            return new BasketFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Home";
        } else if (position == 1) {
            return "Categories";
        } else if (position == 2)
            return "Search";
        else if(position==3)
            return  "Offers";
        else
            return "Basket";
    }
}