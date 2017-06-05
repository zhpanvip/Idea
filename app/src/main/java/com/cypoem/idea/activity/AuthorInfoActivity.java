package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.fragment.AuthorFragment;
import com.cypoem.idea.view.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthorInfoActivity extends BaseActivity {

    @BindView(R.id.iv_author)
    ImageView mIvAuthor;
    @BindView(R.id.tv_pen_name_text)
    TextView mTvPenNameText;
    @BindView(R.id.tv_pen_name)
    TextView mTvPenName;
    @BindView(R.id.tv_sign_text)
    TextView mTvSignText;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.tv_birthday_text)
    TextView mTvBirthdayText;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.tv_address_text)
    TextView mTvAddressText;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_focus)
    TextView mTvFocus;
    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.tv_like)
    TextView mTvLike;
    @BindView(R.id.tv_fans)
    TextView mTvFans;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.ll_focus)
    LinearLayout llFocus;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;

    @BindView(R.id.vp_author)
    ViewPager mViewPager;
    private CommonFragmentAdapter mAdapter;
    @BindView(R.id.tl_author)
    TabLayout mTabLayout;
    @BindView(R.id.sl_view)
    ScrollableLayout mScrollView;

    List<AuthorFragment> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_author_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        setListener();
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollView.getHelper().setCurrentScrollableContainer(mList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        AuthorFragment fragmentStart = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentJoin = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate1 = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate2 = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate3 = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate4 = AuthorFragment.getFragment(new Bundle());
        mList.add(fragmentStart);
        mList.add(fragmentJoin);
        mList.add(fragmentCreate);
        mList.add(fragmentCreate1);
        mList.add(fragmentCreate2);
        mList.add(fragmentCreate3);
        mList.add(fragmentCreate4);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.setFragmentList(mList);
        mViewPager.setAdapter(mAdapter);
        mScrollView.getHelper().setCurrentScrollableContainer(mList.get(0));

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthorInfoActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.ll_collect, R.id.ll_like, R.id.ll_fans, R.id.ll_focus, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_collect:
                CollectActivity.start(this);
                break;
            case R.id.ll_like:
                PraiseActivity.start(this);
                break;
            case R.id.ll_fans:
                FansActivity.start(this);
                break;
            case R.id.ll_focus:
                FansActivity.start(this);
                break;
            case R.id.iv_edit:
                setNightMode();
                break;
        }
    }
}
