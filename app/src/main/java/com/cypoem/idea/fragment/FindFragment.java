package com.cypoem.idea.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.SearchActivity;
import com.cypoem.idea.adapter.CommonFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhpan on 2017/4/21.
 */

public class FindFragment extends BaseFragment {

    @BindView(R.id.rb_left)
    RadioButton mRbLeft;
    @BindView(R.id.rb_right)
    RadioButton mRbRight;
    @BindView(R.id.rg_selector)
    RadioGroup mRgSelector;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.vp_find)
    ViewPager mViewPager;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.et_search_text)
    EditText editText;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tl_find)
    TabLayout mTabLayout;

    private List<FindInFragment> mList;
    private String[] mTitleList;
    public final static int NEWEST = 1;
    public final static int HOTTEST = 0;
    public final static int DALUANDOU=3;
    public final static int POEM=4;
    public final static int VIDEO=5;
    public final static int WULITOU=6;
    public final static int STORY=7;
    public final static int ENCOURAGEMENT=8;
    public final static int CUSTOM=200;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(0);
        }
    }

    private void initData() {
        mTitle.setVisibility(View.GONE);
        // mRgSelector.setVisibility(View.VISIBLE);
       // mIvRight.setVisibility(View.VISIBLE);
       // mIvRight.setBackgroundResource(R.drawable.ic_search);
        mLlSearch.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        mTvSearch.setVisibility(View.VISIBLE);
        mTvCancel.setText("搜索");
        setViewPager();
        setListener();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        }
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
               /* if (position == 0) {
                    mRbLeft.setChecked(true);
                } else if (position == 1) {
                    mRbRight.setChecked(true);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setViewPager() {
        mList = new ArrayList<>();
        mTitleList=getActivity().getApplication().getResources().getStringArray(R.array.type);

        mList.add(FindInFragment.getFragment(NEWEST));
        mList.add(FindInFragment.getFragment(HOTTEST));
        mList.add(FindInFragment.getFragment(DALUANDOU));
        mList.add(FindInFragment.getFragment(POEM));
        mList.add(FindInFragment.getFragment(VIDEO));
        mList.add(FindInFragment.getFragment(WULITOU));
        mList.add(FindInFragment.getFragment(STORY));
        mList.add(FindInFragment.getFragment(ENCOURAGEMENT));
        mList.add(FindInFragment.getFragment(CUSTOM));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CommonFragmentAdapter mFragmentAdapter = new CommonFragmentAdapter(fragmentManager, getContext());
        mFragmentAdapter.setFragmentList(mList);
        mFragmentAdapter.setPageTitle(mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.rb_left, R.id.rb_right, R.id.iv_right,R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_left:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_right:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.iv_right:
                SearchActivity.start(getContext());
                break;
            case R.id.ll_search:
                toSearch();
                break;
        }
    }

    private void toSearch() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent, ActivityOptions
                .makeSceneTransitionAnimation(getActivity()
                        , mLlSearch, "sharedSearch").toBundle());
    }

}
