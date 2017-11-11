package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.cypoem.idea.fragment.FindFragment.DALUANDOU;
import static com.cypoem.idea.fragment.FindFragment.HOTTEST;
import static com.cypoem.idea.fragment.FindFragment.NEWEST;
import static com.cypoem.idea.fragment.FindFragment.POEM;
import static com.cypoem.idea.fragment.FindFragment.VIDEO;

/**
 * Created by zhpan on 2017/4/21.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tl_find)
    TabLayout mTabLayout;
    @BindView(R.id.vp_find)
    ViewPager mViewPager;
    private String[] mTitleList;
    private List<FindInFragment> mList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }


    @Override
    protected void init(Bundle savedInstanceState) {

        initData();
        setViewPager();
        setListener();
        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(0);
        }
    }

    private void setViewPager() {
        mTitleList=getActivity().getApplication().getResources().getStringArray(R.array.message);
        mList=new ArrayList<>();
        mList.add(FindInFragment.getFragment(NEWEST));
        mList.add(FindInFragment.getFragment(HOTTEST));
        mList.add(FindInFragment.getFragment(DALUANDOU));
        mList.add(FindInFragment.getFragment(POEM));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CommonFragmentAdapter mFragmentAdapter = new CommonFragmentAdapter(fragmentManager, getContext());
        mFragmentAdapter.setFragmentList(mList);
        mFragmentAdapter.setPageTitle(mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setListener() {

    }

    private void initData() {
        toolbarTitle.setText("我的消息");
    }

    @OnClick({})
    public void onViewClicked(View view) {

    }

}
