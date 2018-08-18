package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.airong.core.utils.Utils;
import com.cypoem.idea.adapter.AdapterFragmentPager;
import com.cypoem.idea.event.LogoutEvent;
import com.cypoem.idea.event.NightModeEvent;
import com.cypoem.idea.R;
import com.cypoem.idea.fragment.AddFragment;
import com.cypoem.idea.utils.SharedPreferencesHelper;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.MViewPaper;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_find)
    RadioButton mRbFind;
    @BindView(R.id.rb_add)
    RadioButton mRbAdd;
    @BindView(R.id.rb_message)
    RadioButton mRbMessage;
    @BindView(R.id.rb_me)
    RadioButton mRbMe;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.vp_fragment)
    MViewPaper mViewPager;
    @BindView(R.id.iv_add)
    ImageView mIvBackground;
    //  退出时间间隔
    private long exitTime = 0;
    //  上一次RadioGroup选中的Id
    private int preCheckedId = R.id.rb_home;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  友盟统计统计下载量（并不准确）
        boolean isFirst = (boolean) SharedPreferencesHelper.get(this, "isFirstIn", false);
        if(!isFirst){
            MobclickAgent.onEvent(this,"download");
        }
        SharedPreferencesHelper.put(this, "isFirstIn", true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initData();
        setListener();
        reStartActivity();
    }

    //  改变夜间模式后返回时重启Activity
    private void reStartActivity() {
        Intent intent = getIntent();
        boolean nightMode = intent.getBooleanExtra("nightMode", false);
        if (nightMode && UserInfoTools.getIsLogin(this)) {
            //  自动切换到“我的”页面
            mRbMe.performClick();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
        AdapterFragmentPager mAdapter = new AdapterFragmentPager(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    private boolean isLogin() {
        return UserInfoTools.getIsLogin(this);
    }

    private void setListener() {
        rgTab.setOnCheckedChangeListener((RadioGroup group, @IdRes int checkedId) -> {
            switch (checkedId) {
                case R.id.rb_home:
                    setStatusBarColor(R.color.colorPrimaryDark);
                    mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_HOME, false);
                    break;
                case R.id.rb_find:
                    setStatusBarColor(R.color.colorPrimaryDark);
                    mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_FIND, false);
                    break;
                case R.id.rb_add:
                    setStatusBarColor(R.color.colorPrimaryDark);
                    mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_PUBLISH, false);
                    break;
                case R.id.rb_message:
                    if (messageClicked()) {
                        return;
                    }
                    break;
                case R.id.rb_me:
                    if (meClicked()) {
                        return;
                    }
                    break;

            }
            preCheckedId = checkedId;
        });

    }

    private boolean meClicked() {
        if (isLogin()) {
            mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_ME, false);
            setStatusBarColor(R.color.themDark);
            return false;
        } else {
            goToLogin();
            rgTab.check(preCheckedId);
            return true;
        }
    }

    private boolean messageClicked() {
        if (isLogin()) {
            setStatusBarColor(R.color.colorPrimaryDark);
            mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_MESSAGE, false);
            return false;
        } else {
            goToLogin();
            rgTab.check(preCheckedId);
            return true;
        }
    }

    private void goToLogin() {
        LoginActivity.start(this);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mIvBackground.getVisibility() == View.VISIBLE) {
                mIvBackground.setVisibility(View.GONE);
                return true;
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                // finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.iv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                mIvBackground.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 接收到夜间模式改变的事件后结束当前Activity
     *
     * @param event
     */
    @Subscribe
    public void setNightMode(NightModeEvent event) {
        finish();
    }

    /**
     * 接受退出事件
     *
     * @param event
     */
    @Subscribe
    public void logout(LogoutEvent event) {
        mRbHome.setChecked(true);
    }

    /**
     * 接受点击参与已有作品的事件
     *
     * @param joinOpus
     */
    @Subscribe
    public void joinOpus(AddFragment.JoinOpus joinOpus) {
        mIvBackground.setVisibility(View.VISIBLE);
    }
}
