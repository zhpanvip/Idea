package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cypoem.idea.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhpan on 2017/4/21.
 */

public class AddFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        toolbarTitle.setText("发布作品");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setTextColor(Color.parseColor("#83944B"));
        toolbarSubtitle.setText("意见反馈");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_subtitle, R.id.toolbar_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                break;
            case R.id.toolbar_title:
                break;
        }
    }
}
