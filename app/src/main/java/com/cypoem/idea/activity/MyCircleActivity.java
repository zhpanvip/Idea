package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.fragment.MyCircleFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MyCircleActivity extends BaseActivity {

    @BindView(R.id.tl_find)
    TabLayout mTabLayout;
    @BindView(R.id.vp_circle)
    ViewPager mViewPager;
    private int position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        setViewPager();
    }

    private void setViewPager() {
        String[] mTitleList = getApplication().getResources().getStringArray(R.array.my_circle);
        ArrayList<MyCircleFragment> mList = new ArrayList<>();
        mList.add(MyCircleFragment.getFragment(MyCircleFragment.MY_CIRCLE));
        mList.add(MyCircleFragment.getFragment(MyCircleFragment.FOCUS_CIRCLE));
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommonFragmentAdapter mFragmentAdapter = new CommonFragmentAdapter(fragmentManager, this);
        mFragmentAdapter.setFragmentList(mList);
        mFragmentAdapter.setPageTitle(mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(position);
    }

    public static void start(Context context, int position) {
        Intent intent = new Intent(context, MyCircleActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
