package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airong.core.utils.AppUtils;
import com.airong.core.utils.CleanUtils;
import com.airong.core.utils.FileUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.event.LogoutEvent;
import com.cypoem.idea.event.NightModeEvent;
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

    private boolean isChangeNightMode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setData();
        setListener();
        if (savedInstanceState != null) {
            isChangeNightMode = true;
        }
    }

    private void setListener() {
        mToggleButton.setOnClickListener((View v) -> {
            setNightMode();
        });
    }

    private void setData() {
        getToolbarTitle().setText("设置");
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
            url = Constants.PROTOCOL_NIGHT;
        } else {
            url = Constants.PROTOCOL;
        }
        BasicWebViewActivity.start(this, "协议相关", url);
    }

    //  关于我们
    private void goAboutUs() {
        String url;
        if (UserInfoTools.isNightMode(this)) {
            url = Constants.ABOUT_US_NIGHT;
        } else {
            url = Constants.ABOUT_US;
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

    @Override
    protected void onBackPress() {
        goBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        if (isChangeNightMode) {  //  如果改变了夜间模式，则重启MainActivity
            EventBus.getDefault().post(new NightModeEvent());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("nightMode", true);
            startActivity(intent);
            overridePendingTransition(R.anim.animo_no, R.anim.activity_close);
        }
        finish();
    }

    public void setNightMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        UserInfoTools.setNightMode(this,currentNightMode == Configuration.UI_MODE_NIGHT_NO);
        getDelegate().setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
    }
}