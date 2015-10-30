package com.smartchd.smartchandigarh.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by raghav on 30/10/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles;
    private Fragment[] pagerFragments;

    public HomePagerAdapter(FragmentManager fm, Fragment[] pagerFragments, String[] tabTitles) {
        super(fm);
        this.pagerFragments = pagerFragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return pagerFragments[position];
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
