package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.fragment.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.ll_viewpager)
    LinearLayout llViewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_author_info;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_viewpager, ViewPagerFragment.getFragment(new Bundle()));
        fragmentTransaction.commit();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthorInfoActivity.class);
        context.startActivity(intent);
    }



    @OnClick({R.id.ll_collect, R.id.ll_like, R.id.ll_fans, R.id.ll_focus,R.id.iv_edit})
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
                break;
        }
    }
}
