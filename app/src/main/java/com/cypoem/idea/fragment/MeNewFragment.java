package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeNewFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;


    public static MeNewFragment getFragment() {
        MeNewFragment fragment = new MeNewFragment();

        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        toolbarTitle.setText("我的");
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setBackgroundResource(R.drawable.t3_set);
    }


    @OnClick({R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                SettingActivity.start(getContext());
                break;

        }
    }
}
