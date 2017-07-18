package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airong.core.utils.AppUtils;
import com.airong.core.utils.CleanUtils;
import com.airong.core.utils.FileUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.event.LogoutEvent;
import com.cypoem.idea.utils.SharedPreferencesHelper;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
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
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    @BindView(R.id.tb_night_mode)
    ToggleButton mToggleButton;
    @BindView(R.id.btn_exit)
    Button mBtnExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setData();
        setListener();
    }

    private void setListener() {
        mToggleButton.setOnClickListener((View v) -> setNightMode());
    }

    private void setData() {
        mTvCache.setText(FileUtils.getDirSize(getCacheDir()));
        mTvVersion.setText("V " + AppUtils.getAppVersionName(this));
        mToggleButton.setChecked(UserInfoTools.isNightMode(this));
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_catch, R.id.ll_advice, R.id.rl_about_us, R.id.rl_protocol, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_catch:
                clearCache();
                break;
            case R.id.ll_advice:
                SuggestActivity.start(this, "我的");
                break;
            case R.id.rl_about_us:
                goAboutUs();
                break;
            case R.id.rl_protocol:
                goProtocol();
                break;
            case R.id.btn_exit:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferencesHelper.clear(this);
        UserInfoTools.setIsLogin(this, false);
        EventBus.getDefault().post(new LogoutEvent());
        finish();
    }


    //  协议相关
    private void goProtocol() {
        String url;
        if (UserInfoTools.isNightMode(this)) {
            url = "file:///android_asset/www/protocol_night.html";
        } else {
            url = "file:///android_asset/www/protocol.html";
        }
        BasicWebViewActivity.start(this, "协议相关", url);
    }

    //  关于我们
    private void goAboutUs() {
        String url;
        if (UserInfoTools.isNightMode(this)) {
            url = "file:///android_asset/www/about_us_night.html";
        } else {
            url = "file:///android_asset/www/about_us.html";
        }
        BasicWebViewActivity.start(this, "关于我们", url);
    }

    //  清除缓存
    private void clearCache() {
        showTwoButtonDialog("确定要清除所有缓存吗？", "确定", "取消", (View v) -> {
            if (CleanUtils.cleanInternalCache()) {
                mTvCache.setText(FileUtils.getDirSize(getCacheDir()));
                showToast("缓存已清除");
            } else {
                showToast("清除缓存失败...");
            }
            dismissDialog();
        }, (View v) -> dismissDialog());
    }
}
