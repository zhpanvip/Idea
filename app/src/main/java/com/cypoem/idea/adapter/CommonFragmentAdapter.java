package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by zhpan on 2016/12/18.
 *
 */

public class CommonFragmentAdapter extends FragmentStatePagerAdapter {
   private List<? extends Fragment> mFragmentList;
   private Context mContext;

    public CommonFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public List<? extends Fragment> getmFragmentList() {
        return mFragmentList;
    }

    public void setFragmentList(List<?extends Fragment> fragmentList) {
        this.mFragmentList = fragmentList;
    }

    public CommonFragmentAdapter(FragmentManager fm) {
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
