package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.LoginActivity;
import com.cypoem.idea.activity.PublishActivity;
import com.cypoem.idea.activity.SuggestActivity;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhpan on 2017/4/21.
 */

public class AddFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_publish)
    TextView mTvPublish;
    @BindView(R.id.tv_join)
    TextView mTvJoin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        toolbarTitle.setText("发布作品");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("意见反馈");

    }

    @OnClick({R.id.toolbar_subtitle, R.id.toolbar_title, R.id.tv_publish, R.id.tv_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                SuggestActivity.start(getContext(), "发布");
                break;
            case R.id.toolbar_title:
                break;
            case R.id.tv_publish:
                if (UserInfoTools.getIsLogin(getContext())) {
                    PublishActivity.start(getContext());
                } else {
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.tv_join:
                EventBus.getDefault().post(new JoinOpus());
                break;
        }
    }

    public static class JoinOpus {

    }

}
