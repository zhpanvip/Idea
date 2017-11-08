package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.cypoem.idea.R;

import butterknife.BindView;

/**
 *
 */
public class HomePageNewFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.few_circle)
    ViewStub mViewStub;

    public static HomePageNewFragment getFragment() {
        return new HomePageNewFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("创建圈子");
        toolbarTitle.setText("关注");
        mViewStub.inflate();
        // mViewStub.inflate();
    }

}
