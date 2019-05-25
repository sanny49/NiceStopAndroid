package com.example.nicestop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        ScanFragment fln = new ScanFragment();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
