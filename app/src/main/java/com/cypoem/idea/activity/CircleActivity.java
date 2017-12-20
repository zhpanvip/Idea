package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.fragment.CircleFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class CircleActivity extends BaseActivity {

    @BindView(R.id.tl_find)
    TabLayout mTabLayout;
    @BindView(R.id.vp_circle)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setViewPager();
    }

    private void setViewPager() {
        String[] mTitleList = getApplication().getResources().getStringArray(R.array.circle_type);
        ArrayList<CircleFragment> mList = new ArrayList<>();
        mList.add(CircleFragment.getFragment(CircleFragment.HOT));
        mList.add(CircleFragment.getFragment(CircleFragment.TIME));
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommonFragmentAdapter mFragmentAdapter = new CommonFragmentAdapter(fragmentManager, this);
        mFragmentAdapter.setFragmentList(mList);
        mFragmentAdapter.setPageTitle(mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CircleActivity.class);
        context.startActivity(intent);
    }
}
