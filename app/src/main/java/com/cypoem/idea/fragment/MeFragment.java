package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airong.core.utils.AppUtils;
import com.airong.core.utils.CleanUtils;
import com.airong.core.utils.FileUtils;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.activity.BasicWebViewActivity;
import com.cypoem.idea.activity.EditInfoActivity;
import com.cypoem.idea.activity.SettingActivity;
import com.cypoem.idea.activity.SuggestActivity;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.event.HideView;
import com.cypoem.idea.event.NightModeEvent;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.CollectActivity;
import com.cypoem.idea.activity.FansActivity;
import com.cypoem.idea.activity.OpusActivity;
import com.cypoem.idea.activity.PraiseActivity;
import com.cypoem.idea.activity.WalletActivity;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/4/21.
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.rl_publish)
    RelativeLayout rlPublish;
    @BindView(R.id.rl_join)
    RelativeLayout rlJoin;
    @BindView(R.id.rl_create)
    RelativeLayout rlCreate;
    @BindView(R.id.rl_draft)
    RelativeLayout rlDraft;
    @BindView(R.id.rl_wallet)
    RelativeLayout rlWallet;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.ll_focus)
    LinearLayout llFocus;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.ll_main)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar_title)
    TextView mTvTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private UserBean userBean;

    public static MeFragment getFragment(Bundle bundle) {
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            scrollView.scrollTo(savedInstanceState.getInt("scrollX"), savedInstanceState.getInt("scrollY"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        setTitleBar();

        EventBus.getDefault().register(this);
        userBean = UserInfoTools.getUser(getContext());
        setUserInfo();
        getData();
    }

    private void setUserInfo() {
        ImageLoaderUtil.loadCircleImg(headImg, IdeaApiService.HOST + userBean.getIcon(), R.drawable.head_pic);
        tvFocus.setText(String.valueOf(userBean.getMyWatchCount()));
        tvFans.setText(String.valueOf(userBean.getWatchMeCount()));
        tvCollect.setText(String.valueOf(userBean.getKeep_count()));
        tvLike.setText(String.valueOf(userBean.getEnjoy_count()));
        tvName.setText(userBean.getPen_name());
        String dictum = userBean.getDictum();
        if (TextUtils.isEmpty(dictum)) {
            tvSign.setVisibility(View.GONE);
        } else {
            tvSign.setText(dictum);
        }
    }

    private void setTitleBar() {
        mTvTitle.setText("我的创意说");
        mTvTitle.setTextColor(Color.WHITE);
        mIvRight.setBackgroundResource(R.drawable.ic_setting);
        mIvRight.setVisibility(View.VISIBLE);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.me_background));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void updateInfoSuccess(EditInfoActivity.UpdateInfoSuccess success) {
        setUserInfo();
    }


    @OnClick({R.id.ll_focus, R.id.ll_fans, R.id.ll_collect, R.id.rl_wallet, R.id.ll_like
            , R.id.rl_join, R.id.rl_create, R.id.rl_publish, R.id.rl_draft, R.id.head_img, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_focus:
                FansActivity.start(getContext(), Constants.FOCUS, UserInfoTools.getUserId(getContext()));
                break;
            case R.id.ll_collect:
                CollectActivity.start(getContext());
                break;
            case R.id.ll_fans:
                FansActivity.start(getContext(), Constants.FOLLOWS, UserInfoTools.getUserId(getContext()));
                break;
            case R.id.ll_like:
               // PraiseActivity.start(getContext());
                break;
            case R.id.rl_wallet:
                WalletActivity.start(getContext());
                break;
            case R.id.rl_publish:
                OpusActivity.start(getContext(), Constants.MY_START_OPUS);
                break;
            case R.id.rl_create:
                OpusActivity.start(getContext(), Constants.MY_OWN_OPUS);
                break;
            case R.id.rl_join:
                OpusActivity.start(getContext(), Constants.MY_JOIN_OPUS);
                break;
            case R.id.rl_draft:
                OpusActivity.start(getContext(), Constants.MY_DRAFT);
                break;
            case R.id.head_img:
                AuthorInfoActivity.start(getContext(), UserInfoTools.getUserId(getContext()));
                break;
            case R.id.iv_right:
                SettingActivity.start(getContext());
                break;
        }
    }


    private void getData() {
        String userId = UserInfoTools.getUserId(getContext());
        IdeaApi.getApiService()
                .getUserInfo(userId,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        userBean = response.getResult();
                        UserInfoTools.setUser(getContext(),userBean);
                        setUserInfo();
                    }
                });
    }
}
