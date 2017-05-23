package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.BaseFragmentAdapter;
import com.cypoem.idea.fragment.AuthorFragment;
import com.cypoem.idea.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class AuthorInfoActivity extends BaseActivity {

    @BindView(R.id.vp_author)
    ViewPager mViewPager;
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
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.view_start)
    View mViewStart;
    @BindView(R.id.ll_start)
    LinearLayout mLlStart;
    @BindView(R.id.tv_join)
    TextView mTvJoin;
    @BindView(R.id.view_join)
    View mViewJoin;
    @BindView(R.id.ll_join)
    LinearLayout mLlJoin;
    @BindView(R.id.tv_create)
    TextView mTvCreate;
    @BindView(R.id.view_create)
    View mViewCreate;
    @BindView(R.id.ll_create)
    LinearLayout mLlCreate;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    private BaseFragmentAdapter mAdapter;
    private int prePosition;
    private List<View> mLineList;
    private List<TextView> mListTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_author_info;
    }

    @Override
    protected void init() {
        initData();
        setListener();

    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mLineList.get(prePosition).setBackgroundColor(Color.parseColor("#FFFFFF"));
                mListTv.get(prePosition).setTextColor(Color.parseColor("#666666"));
                mLineList.get(position).setBackgroundColor(Color.parseColor("#628B31"));
                mListTv.get(position).setTextColor(Color.parseColor("#628B31"));
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initData() {
        List<BaseFragment> mList = new ArrayList<>();
        mLineList = new ArrayList<>();
        mListTv = new ArrayList<>();
        mLineList.add(mViewStart);
        mLineList.add(mViewJoin);
        mLineList.add(mViewCreate);

        mListTv.add(mTvStart);
        mListTv.add(mTvJoin);
        mListTv.add(mTvCreate);

        AuthorFragment fragmentStart = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentJoin = AuthorFragment.getFragment(new Bundle());
        AuthorFragment fragmentCreate = AuthorFragment.getFragment(new Bundle());
        mList.add(fragmentStart);
        mList.add(fragmentJoin);
        mList.add(fragmentCreate);
        mAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.setFragmentList(mList);
        mViewPager.setAdapter(mAdapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthorInfoActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.ll_start, R.id.ll_join, R.id.ll_create, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.ll_join:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.ll_create:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.iv_edit:
                break;
        }
    }


}
