package com.example.nicestop;


import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pagerAdapter;
    TabItem tabScan;
    TabItem tabPlateNumber;
    TabItem tabViolation;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabScan = view.findViewById(R.id.tabScan);
        tabPlateNumber = view.findViewById(R.id.tabPlateNumber);
        tabViolation = view.findViewById(R.id.tabViolation);
        viewPager = view.findViewById(R.id.viewPager);
        pagerAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //if(tab.getPosition() == 1){
                //    toolbar.setBackground(ContextCompat.getColor(Ticket2.this,R.color.colorAccent));
                //    tabLayout.setBackground(ContextCompat.getColor(Ticket2.this,R.color.colorAccent));
                //}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return  view;
    }

}
