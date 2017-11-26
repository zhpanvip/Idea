package com.cypoem.idea.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.fragment.RankingListFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class RankingListActivity extends BaseActivity {


    @BindView(R.id.tl_find)
    TabLayout mTabLayout;
    @BindView(R.id.vp_find)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String title = bundle.getString("title");
        setToolBarTitle(title);
        setViewPager();
    }

    private void setViewPager() {
        String[] mTitleList = getApplication().getResources().getStringArray(R.array.ranking_type);
        ArrayList<RankingListFragment> mList = new ArrayList<>();
        mList.add(RankingListFragment.getFragment(RankingListFragment.DONATE));
        mList.add(RankingListFragment.getFragment(RankingListFragment.GET));
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommonFragmentAdapter mFragmentAdapter = new CommonFragmentAdapter(fragmentManager, this);
        mFragmentAdapter.setFragmentList(mList);
        mFragmentAdapter.setPageTitle(mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
