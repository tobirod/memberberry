package com.newton.tr.member.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newton.tr.member.fragments.TabNote;
import com.newton.tr.member.fragments.TabShop;
import com.newton.tr.member.fragments.TabTask;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabsCount;

    public FragmentPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mTabsCount = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabTask();
            case 1:
                return new TabShop();
            case 2:
                return new TabNote();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabsCount;
    }
}
