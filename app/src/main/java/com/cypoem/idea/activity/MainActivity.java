package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cypoem.idea.adapter.AdapterFragmentPager;
import com.cypoem.idea.event.HideView;
import com.cypoem.idea.event.LogoutEvent;
import com.cypoem.idea.event.NightModeEvent;
import com.cypoem.idea.R;
import com.cypoem.idea.fragment.AddFragment;
import com.cypoem.idea.fragment.FindFragment;
import com.cypoem.idea.fragment.HomePageFragment;
import com.cypoem.idea.fragment.MeFragment;
import com.cypoem.idea.fragment.MessageFragment;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.MViewPaper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

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
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.vp_fragment)
    MViewPaper mViewPager;
    //  退出时间间隔
    private long exitTime = 0;
    //  上一次RadioGroup选中的Id
    private int preCheckedId;
    private AdapterFragmentPager mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        if(nightMode&&UserInfoTools.getIsLogin(this)){
            mRbMe.performClick();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void setNightMode(NightModeEvent event) {
        finish();
    }

    @Subscribe
    public void logout(LogoutEvent event){
        mRbHome.setChecked(true);
    }

    private void initData() {

        getToolbar().setVisibility(View.GONE);
        mAdapter = new AdapterFragmentPager(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
    }

    private boolean isLogin() {
        return UserInfoTools.getIsLogin(this);
    }

    private void setListener() {
        rgTab.setOnCheckedChangeListener((RadioGroup group, @IdRes int checkedId) -> {
            switch (checkedId) {
                case R.id.rb_home:
                    mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_HOME, false);
                    break;
                case R.id.rb_find:
                    mViewPager.setCurrentItem(AdapterFragmentPager.PAGE_FIND, false);
                    break;
                case R.id.rb_add:
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
            return false;
        } else {
            goToLogin();
            rgTab.check(preCheckedId);
            return true;
        }
    }

    private boolean messageClicked() {
        if (isLogin()) {
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

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
