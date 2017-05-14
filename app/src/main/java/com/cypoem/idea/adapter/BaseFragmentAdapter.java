package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by zhpan on 2016/12/18.
 */

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {
    List<? extends Fragment> mFragmentList;
    Context mContext;

    public BaseFragmentAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    public List<? extends Fragment> getmFragmentList() {
        return mFragmentList;
    }

    public void setmFragmentList(List<?extends Fragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
