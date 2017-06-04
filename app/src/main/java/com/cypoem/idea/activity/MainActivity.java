package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cypoem.idea.event.HideView;
import com.cypoem.idea.event.NightModeEvent;
import com.cypoem.idea.R;
import com.cypoem.idea.fragment.AddFragment;
import com.cypoem.idea.fragment.FindFragment;
import com.cypoem.idea.fragment.HomePageFragment;
import com.cypoem.idea.fragment.MeFragment;
import com.cypoem.idea.fragment.MessageFragment;
import com.cypoem.idea.utils.UserInfoTools;
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

    private HomePageFragment mHomePageFragment;
    private FindFragment mFindFragment;
    private AddFragment mAddFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;
    private FragmentManager mFragmentManger;
    private long exitTime = 0;
    private int prePosition;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void init() {
        initData();
        setListener();
        mRbHome.performClick();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void setNightMode(NightModeEvent event) {
        if (event.isNight) {
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    private void initData() {
        mFragmentManger = getSupportFragmentManager();
        getToolbar().setVisibility(View.GONE);
    }

    private void setListener() {
        rgTab.setOnCheckedChangeListener((RadioGroup group, @IdRes int checkedId) -> {

            FragmentTransaction fragmentTransaction = mFragmentManger.beginTransaction();
            switch (checkedId) {
                case R.id.rb_home:
                    homeClicked(fragmentTransaction);
                    break;
                case R.id.rb_find:
                    findClicked(fragmentTransaction);
                    break;
                case R.id.rb_add:
                    addClicked(fragmentTransaction);
                    break;
                case R.id.rb_message:
                    boolean isToLogin = messageClicked(fragmentTransaction);
                    if(isToLogin){
                        return;
                    }
                    break;
                case R.id.rb_me:
                    meClicked(fragmentTransaction);
                    break;
            }
            fragmentTransaction.commit();
            prePosition = checkedId;
        });
    }

    private void meClicked(FragmentTransaction fragmentTransaction) {
        goToMe(fragmentTransaction);
        /*if (UserInfoTools.getIsLogin(this)) {
            goToMe(fragmentTransaction);
        } else {
            goToLogin();
        }*/
    }

    private boolean messageClicked(FragmentTransaction fragmentTransaction) {
        if (UserInfoTools.getIsLogin(this)) {
            goToMessage(fragmentTransaction);
            return false;
        } else {
            goToLogin();
            rgTab.check(prePosition);
            return true;
        }
    }

    private void addClicked(FragmentTransaction fragmentTransaction) {
        hideAllFragment(fragmentTransaction);
        if (mAddFragment == null) {
            mAddFragment = new AddFragment();
            fragmentTransaction.add(R.id.fl_fragment, mAddFragment);
        } else {
            fragmentTransaction.show(mAddFragment);
        }
    }

    private void findClicked(FragmentTransaction fragmentTransaction) {
        hideAllFragment(fragmentTransaction);
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
            fragmentTransaction.add(R.id.fl_fragment, mFindFragment);
        } else {
            fragmentTransaction.show(mFindFragment);
        }
    }

    private void homeClicked(FragmentTransaction fragmentTransaction) {
        hideAllFragment(fragmentTransaction);
        if (mHomePageFragment == null) {
            mHomePageFragment = HomePageFragment.getInstance(0);
            fragmentTransaction.add(R.id.fl_fragment, mHomePageFragment);
        } else {
            fragmentTransaction.show(mHomePageFragment);
        }
    }

    private void goToMessage(FragmentTransaction fragmentTransaction) {
        hideAllFragment(fragmentTransaction);
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
            fragmentTransaction.add(R.id.fl_fragment, mMessageFragment);
        } else {
            fragmentTransaction.show(mMessageFragment);
        }
    }

    private void goToMe(FragmentTransaction fragmentTransaction) {
        hideAllFragment(fragmentTransaction);
        if (mMeFragment == null) {
            Bundle bundle = new Bundle();
            mMeFragment = MeFragment.getFragment(bundle);
            fragmentTransaction.add(R.id.fl_fragment, mMeFragment);
        } else {
            fragmentTransaction.show(mMeFragment);
            //  发送隐藏MeFragment中footer的消息
            EventBus.getDefault().post(new HideView(true));
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

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mHomePageFragment != null) fragmentTransaction.hide(mHomePageFragment);
        if (mFindFragment != null) fragmentTransaction.hide(mFindFragment);
        if (mAddFragment != null) fragmentTransaction.hide(mAddFragment);
        if (mMessageFragment != null) fragmentTransaction.hide(mMessageFragment);
        if (mMeFragment != null) fragmentTransaction.hide(mMeFragment);
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
