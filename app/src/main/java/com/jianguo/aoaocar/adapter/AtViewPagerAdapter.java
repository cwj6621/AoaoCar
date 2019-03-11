package com.jianguo.aoaocar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 22077 on 2017/9/8.
 */

public class AtViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    String[] mTitles=null;
    public AtViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> _list, String[] mTitles) {
        super(fm);
        this.list = _list;
        this.mTitles=mTitles;
    }
    public AtViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> _list ) {
        super(fm);
        this.list = _list;
        this.mTitles=mTitles;
    }
    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
