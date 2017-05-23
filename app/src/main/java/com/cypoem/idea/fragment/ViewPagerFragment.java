package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhpan on 2017/5/23.
 */

public class ViewPagerFragment extends BaseFragment {
    @BindView(R.id.vp_author)
    ViewPager mViewPager;
    @BindView(R.id.tl_author)
    TabLayout mTabLayout;
    private BaseFragmentAdapter mAdapter;

    public static ViewPagerFragment getFragment(Bundle bundle) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        List<BaseFragment> mList = new ArrayList<>();
        AuthorFragment fragmentStart = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentJoin = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate = AuthorFragment.getFragment(new Bundle());
        mList.add(fragmentStart);
        mList.add(fragmentJoin);
        mList.add(fragmentCreate);
        mAdapter = new BaseFragmentAdapter(getActivity().getSupportFragmentManager(), getContext());
        mAdapter.setFragmentList(mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


}
