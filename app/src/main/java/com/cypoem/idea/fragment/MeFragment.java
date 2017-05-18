package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cypoem.idea.R;

/**
 * Created by zhpan on 2017/4/21.
 */
public class MeFragment extends BaseFragment {
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private RelativeLayout mRelativeLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        mAppBarLayout = (AppBarLayout) rootView.findViewById(R.id.apl_main);
        mToolbar = (Toolbar) rootView.findViewById(R.id.tb_main);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.ctl_main);
        mRelativeLayout= (RelativeLayout) rootView.findViewById(R.id.ll_main);
        initData();
    }

    private void initData() {
        //  设置ToolBar信息
        mCollapsingToolbarLayout.setTitle("我的创意说");
        mCollapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);
        mAppBarLayout.addOnOffsetChangedListener((AppBarLayout appBarLayout, int verticalOffset) -> {
            if (verticalOffset <= -(2 * mRelativeLayout.getHeight()) / 3) {
                mCollapsingToolbarLayout.setTitle("我的创意说");
            } else {
                mCollapsingToolbarLayout.setTitle("");
            }
        });
    }

}
