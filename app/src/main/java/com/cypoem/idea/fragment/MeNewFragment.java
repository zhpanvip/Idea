package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.BaseActivity;
import com.cypoem.idea.activity.FansActivity;
import com.cypoem.idea.activity.PraiseActivity;
import com.cypoem.idea.activity.SettingActivity;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.utils.UserInfoTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeNewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rl_user)
    RelativeLayout mRlUser;

    @OnClick({R.id.rl_user,R.id.ll_follow,R.id.ll_fans,R.id.ll_reward})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_user:
                AuthorInfoActivity.start(getContext(),UserInfoTools.getUserId(getContext()));
                break;
            case R.id.ll_follow:
                FansActivity.start(getContext(), Constants.FOCUS, UserInfoTools.getUserId(getContext()));
                break;
            case R.id.ll_fans:
                FansActivity.start(getContext(), Constants.FOLLOWS, UserInfoTools.getUserId(getContext()));
                break;
            case R.id.ll_reward:
                PraiseActivity.start(getContext());
                break;
        }
    }

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
        setRefreshLayout(true);
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

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
