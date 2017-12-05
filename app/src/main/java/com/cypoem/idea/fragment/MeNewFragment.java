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

import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.FansActivity;
import com.cypoem.idea.activity.PraiseActivity;
import com.cypoem.idea.activity.SettingActivity;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.SexView;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.rl_user)
    RelativeLayout mRlUser;
    @BindView(R.id.tv_username)
    TextView mTvUserName;
    @BindView(R.id.iv_icon)
    CircleImageView circleImageView;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.sex_view)
    SexView mSexView;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;
    @BindView(R.id.tv_award)
    TextView mTvAward;
    @BindView(R.id.tv_fans)
    TextView mTvFans;



    @OnClick({R.id.rl_user, R.id.ll_follow, R.id.ll_fans, R.id.ll_reward})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user:
                AuthorInfoActivity.start(getContext(), UserInfoTools.getUserId(getContext()));
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
        setUserData();
        // setRefreshLayout(true);
    }

    private void initData() {
        toolbarTitle.setText("我的");
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setBackgroundResource(R.drawable.t3_set);

    }

    private void setUserData() {
        UserBean user = UserInfoTools.getUser(getContext());
        mTvUserName.setText(user.getPen_name());
        ImageLoaderUtil.loadImg(circleImageView, IdeaApiService.HOST+user.getIcon());
        mTvSign.setText(user.getDictum());
        mSexView.setMalePercent(user.getSex());
        mTvAward.setText(String.valueOf(user.getIncome_count()));
        mTvFans.setText(String.valueOf(user.getWatchMeCount()));
        mTvFollow.setText(String.valueOf(user.getMyWatchCount()));
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
