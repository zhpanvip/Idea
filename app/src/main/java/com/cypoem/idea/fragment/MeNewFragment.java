package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeNewFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;


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
}
