package com.example.aksha.gjusteve.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.aksha.gjusteve.fragments.home.Events;
import com.example.aksha.gjusteve.fragments.home.PastEvent;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    final private String[] tabName ={"Upcoming Events","Past Events"};
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new Events();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new PastEvent();
            default:
                return null;
        }

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }
}