package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cypoem.idea.R;
import com.cypoem.idea.fragment.AddFragment;
import com.cypoem.idea.fragment.FindFragment;
import com.cypoem.idea.fragment.HomePageFragment;
import com.cypoem.idea.fragment.MeFragment;
import com.cypoem.idea.fragment.MessageFragment;
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
    @BindView(R.id.ll_tab)
    LinearLayout llTab;

    private HomePageFragment mHomePageFragment;
    private FindFragment mFindFragment;
    private AddFragment mAddFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;
    private FragmentManager mFragmentManger;
    private long exitTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void init() {
        initData();
        setListener();
        mRbHome.performClick();
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
                    hideAllFragment(fragmentTransaction);
                    if (mHomePageFragment == null) {
                        mHomePageFragment = HomePageFragment.getInstance(0);
                        fragmentTransaction.add(R.id.ll_fragment, mHomePageFragment);
                    } else {
                        fragmentTransaction.show(mHomePageFragment);
                    }
                    break;
                case R.id.rb_find:
                    hideAllFragment(fragmentTransaction);
                    if (mFindFragment == null) {
                        mFindFragment = new FindFragment();
                        fragmentTransaction.add(R.id.ll_fragment, mFindFragment);
                    } else {
                        fragmentTransaction.show(mFindFragment);
                    }
                    break;
                case R.id.rb_add:
                    hideAllFragment(fragmentTransaction);
                    if (mAddFragment == null) {
                        mAddFragment = new AddFragment();
                        fragmentTransaction.add(R.id.ll_fragment, mAddFragment);
                    } else {
                        fragmentTransaction.show(mAddFragment);
                    }
                    break;
                case R.id.rb_message:
                    hideAllFragment(fragmentTransaction);
                    if (mMessageFragment == null) {
                        mMessageFragment = new MessageFragment();
                        fragmentTransaction.add(R.id.ll_fragment, mMessageFragment);
                    } else {
                        fragmentTransaction.show(mMessageFragment);
                    }
                    break;
                case R.id.rb_me:
                    hideAllFragment(fragmentTransaction);
                    if (mMeFragment == null) {
                        mMeFragment = new MeFragment();
                        fragmentTransaction.add(R.id.ll_fragment, mMeFragment);
                    } else {
                        fragmentTransaction.show(mMeFragment);
                    }
                    break;
            }
            fragmentTransaction.commit();
        });
    }


    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
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
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                showToast( "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

   /* private void getData(boolean showLoading) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this, showLoading) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size() - content.size() + 2; i++) {
                            Toast.makeText(MainActivity.this, "Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

}
