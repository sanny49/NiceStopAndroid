package com.example.nicestop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return new LicenseNumberFragment();
            case 1:
                return new PlateNumberFragment();
            case 2:
                return new ViolationFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount(){ return numOfTabs; }
}