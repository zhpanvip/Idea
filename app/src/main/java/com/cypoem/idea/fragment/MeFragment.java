package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
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
import com.cypoem.idea.activity.SuggestActivity;
import com.cypoem.idea.event.HideView;
import com.cypoem.idea.event.NightModeEvent;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.CollectActivity;
import com.cypoem.idea.activity.FansActivity;
import com.cypoem.idea.activity.OpusActivity;
import com.cypoem.idea.activity.PraiseActivity;
import com.cypoem.idea.activity.WalletActivity;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.rl_night)
    RelativeLayout rlNight;
    @BindView(R.id.rl_catch)
    RelativeLayout rlCatch;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.rl_advice)
    RelativeLayout rlAdvice;
    @BindView(R.id.rl_protocol)
    RelativeLayout rlProtocol;
    @BindView(R.id.tb_toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.ctl_main)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.apl_main)
    AppBarLayout appBarLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.me_view)
    View mView;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    @BindView(R.id.tb_night_mode)
    ToggleButton mToggleButton;
    private boolean isNull;

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
        setData();
        setListener();
        scrollView.scrollBy(0, 500);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("scrollX", scrollView.getScrollX());
        outState.putInt("scrollY", scrollView.getScrollY());
        Log.e("MeFragment", "scrollX:" + scrollView.getScrollX() + "------scrollY:" + scrollView.getScrollY());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            scrollView.scrollTo(savedInstanceState.getInt("scrollX"), savedInstanceState.getInt("scrollY"));
            Log.e("MeFragment", "saved scrollX:" + savedInstanceState.getInt("scrollX") + "------saved scrollY:" + savedInstanceState.getInt("scrollY"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
    }

    private void setListener() {
        mToggleButton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isNull) {
                EventBus.getDefault().post(new NightModeEvent(isChecked));
                UserInfoTools.setNightMode(getContext(), isChecked);
            }
        });
    }

    private void setData() {
        mTvCache.setText(FileUtils.getDirSize(getContext().getCacheDir()));
        mTvVersion.setText("V " + AppUtils.getAppVersionName(getContext()));
        mToggleButton.setChecked(UserInfoTools.isNightMode(getContext()));
    }

    private void initData() {
        EventBus.getDefault().register(this);
        setTitleBar();
        setUserInfo();
    }

    private void setUserInfo() {
        UserBean user = UserInfoTools.getUser(getContext());
        ImageLoaderUtil.loadCircleImg(headImg, user.getIcon(), R.drawable.head_pic);
        tvFocus.setText(user.getMyWatchCount()+"");
        tvFans.setText(user.getWatchMeCount()+"");
        tvCollect.setText(user.getKeep_count()+"");
        tvLike.setText(user.getEnjoy_count()+"");
        tvName.setText(user.getPen_name());
        tvSign.setText(user.getIntroduction());
    }

    private void setTitleBar() {
        //  设置ToolBar信息
        collapsingToolbarLayout.setTitle("我的创意说");
        //collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.START | Gravity.BOTTOM);
        appBarLayout.addOnOffsetChangedListener((AppBarLayout appBarLayout, int verticalOffset) -> {
            if (verticalOffset <= -(2 * mRelativeLayout.getHeight()) / 3) {
                collapsingToolbarLayout.setTitle("我的创意说");
            } else {
                collapsingToolbarLayout.setTitle("");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideFooterView(HideView hideView) {
        if (hideView.isHide) {
            mView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.ll_focus, R.id.ll_fans, R.id.ll_collect, R.id.rl_wallet, R.id.ll_like
            , R.id.rl_join, R.id.rl_create, R.id.rl_publish, R.id.rl_draft, R.id.head_img
            , R.id.rl_catch,R.id.ll_advice,R.id.rl_about_us,R.id.rl_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_focus:
                FansActivity.start(getContext());
                break;
            case R.id.ll_collect:
                CollectActivity.start(getContext());
                break;
            case R.id.ll_fans:
                FansActivity.start(getContext());
                break;
            case R.id.ll_like:
                PraiseActivity.start(getContext());
                break;
            case R.id.rl_wallet:
                WalletActivity.start(getContext());
                break;
            case R.id.rl_publish:
                OpusActivity.start(getContext());
                break;
            case R.id.rl_create:
                OpusActivity.start(getContext());
                break;
            case R.id.rl_join:
                OpusActivity.start(getContext());
                break;
            case R.id.rl_draft:
                OpusActivity.start(getContext());
                break;
            case R.id.head_img:
                AuthorInfoActivity.start(getContext(),UserInfoTools.getUserId(getContext()));
                break;
            case R.id.rl_catch:
                clearCache();
                break;
            case R.id.ll_advice:
                SuggestActivity.start(getContext(),"我");
                break;
            case R.id.rl_about_us:
                goAboutUs();
                break;
            case R.id.rl_protocol:
                goProtocol();
                break;
        }
    }
    //  协议相关
    private void goProtocol() {
        String url;
        if(UserInfoTools.isNightMode(getContext())){
            url="file:///android_asset/www/protocol_night.html";
        }else {
            url="file:///android_asset/www/protocol.html";
        }
        BasicWebViewActivity.start(getContext(),"协议相关",url);
    }
    //  关于我们
    private void goAboutUs() {
        String url;
        if(UserInfoTools.isNightMode(getContext())){
            url="file:///android_asset/www/about_us_night.html";
        }else {
            url="file:///android_asset/www/about_us.html";
        }
        BasicWebViewActivity.start(getContext(),"关于我们",url);
    }
    //  清除缓存
    private void clearCache() {
        showTwoButtonDialog("确定要清除所有缓存吗？", "确定", "取消", (View v) -> {
            if (CleanUtils.cleanInternalCache()) {
                mTvCache.setText(FileUtils.getDirSize(getContext().getCacheDir()));
                showToast("缓存已清除");
            } else {
                showToast("清除缓存失败...");
            }
            dismissDialog();
        }, (View v) -> dismissDialog());
    }
}
