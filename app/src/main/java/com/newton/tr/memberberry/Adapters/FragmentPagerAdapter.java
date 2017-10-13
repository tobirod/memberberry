package com.newton.tr.memberberry.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newton.tr.memberberry.Fragments.TabNote;
import com.newton.tr.memberberry.Fragments.TabShop;
import com.newton.tr.memberberry.Fragments.TabTask;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    int mTabsCount;

    public FragmentPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mTabsCount = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabTask tabTask = new TabTask();
                return tabTask;
            case 1:
                TabShop tabShop = new TabShop();
                return tabShop;
            case 2:
                TabNote tabNote = new TabNote();
                return tabNote;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabsCount;
    }
}