package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.CollectActivity;
import com.cypoem.idea.activity.FansActivity;
import com.cypoem.idea.activity.WalletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhpan on 2017/4/21.
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.rl_publish)
    LinearLayout rlPublish;
    @BindView(R.id.rl_join)
    LinearLayout rlJoin;
    @BindView(R.id.rl_create)
    LinearLayout rlCreate;
    @BindView(R.id.rl_draft)
    LinearLayout rlDraft;
    @BindView(R.id.rl_wallet)
    LinearLayout rlWallet;
    @BindView(R.id.rl_night)
    RelativeLayout rlNight;
    @BindView(R.id.rl_catch)
    RelativeLayout rlCatch;
    @BindView(R.id.rl_update)
    LinearLayout rlUpdate;
    @BindView(R.id.rl_about_us)
    LinearLayout rlAboutUs;
    @BindView(R.id.rl_advice)
    LinearLayout rlAdvice;
    @BindView(R.id.rl_protocol)
    LinearLayout rlProtocol;
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init() {
        initData();

    }

    private void initData() {
        //  设置ToolBar信息
        collapsingToolbarLayout.setTitle("我的创意说");
        //collapsingToolbarLayout.setContentScrimColor(Color.BLUE);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);
        appBarLayout.addOnOffsetChangedListener((AppBarLayout appBarLayout, int verticalOffset) -> {
            if (verticalOffset <= -(2 * mRelativeLayout.getHeight()) / 3) {
                collapsingToolbarLayout.setTitle("我的创意说");
            } else {
                collapsingToolbarLayout.setTitle("");
            }
        });
        ImageLoaderUtil.loadCircleImg(headImg,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=304866327,2141533711&fm=11&gp=0.jpg",R.drawable.head_pic);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_focus,R.id.ll_fans,R.id.ll_collect,R.id.rl_wallet})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_focus:
                FansActivity.start(getContext());
                break;
            case R.id.ll_collect:
                CollectActivity.start(getContext());
                break;
            case R.id.ll_fans:
                FansActivity.start(getContext());
                break;
            case R.id.rl_wallet:
                WalletActivity.start(getContext());
                break;
        }
    }
}
