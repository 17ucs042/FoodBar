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
            return new Fragment1();
        }
        else if (position == 1) {
            return new Fragment2();
        } else if (position == 2) {
            return new Fragment3();
        } else if(position == 3)
            return new Fragment4();
        else
        {
            return new Fragment4();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return mContext.getString(R.string.home);
        } else if (position == 1) {
            return mContext.getString(R.string.products);
        } else if (position == 2)
            return mContext.getString(R.string.career);
        else
            return mContext.getString(R.string.offers);
    }*/
}