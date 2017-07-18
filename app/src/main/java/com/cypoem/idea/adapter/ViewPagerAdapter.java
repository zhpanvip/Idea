package com.cypoem.idea.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * Created by zhpan on 2017/1/15.
 * 报表页面ViewPager的适配器
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mList;

    public List<Fragment> getmList() {
        return mList;
    }

    public void setmList(List<Fragment> mList) {
        this.mList = mList;
    }


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
