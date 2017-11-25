package com.cypoem.idea.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airong.core.BaseCoreActivity;
import com.airong.core.BaseCoreFragment;
import com.airong.core.BaseRxActivity;
import com.airong.core.dialog.CustomDialog;
import com.airong.core.dialog.DialogUtils;
import com.airong.core.utils.BarUtils;
import com.airong.core.view.PtrClassicListFooter;
import com.airong.core.view.PtrClassicListHeader;
import com.cypoem.idea.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends BaseCoreActivity {

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    LinearLayout parentLinearLayout;
    //把父类activity和子类activity的view都add到这里
   // private LinearLayout parentLinearLayout;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    //  对话框
    private CustomDialog dialog;
    PtrClassicFrameLayout mPtrFrame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setTranslucentStatus(this,true);
        /*StatusBarManager.getInstance().setActivityWindowStyle(getWindow());
        StatusBarManager.getInstance().setStatusBar(getWindow(), Color.TRANSPARENT);*/
        initContentView(R.layout.activity_base);
        setStatusBarColor(R.color.white);
        BarUtils.StatusBarLightMode(this);
        //  注入子Activity布局
        setContentView(getLayoutId());
        initToolBar();
        init(savedInstanceState);

    }

        //当你确定要使用沉浸式模式，那么只需要重写Activity的onWindowFocusChanged()方法，然后加入如下逻辑即可
   /* @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    protected abstract
    @LayoutRes
    int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //  初始化刷新加载框架，子类中需要的时候调用
    protected void initPtr(boolean isAutoRefresh) {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.list_view_frame);
        if (mPtrFrame == null) return;
        mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        PtrClassicListHeader header = new PtrClassicListHeader(this);
        header.setLastUpdateTimeRelateObject(this);
        PtrClassicListFooter footer = new PtrClassicListFooter(this);
        footer.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setFooterView(footer);
        mPtrFrame.addPtrUIHandler(footer);

        setPtrHandler(null);

        mPtrFrame.setKeepHeaderWhenRefresh(true);
        if (isAutoRefresh)
            mPtrFrame.postDelayed((() -> mPtrFrame.autoRefresh()), 1000);
    }

    protected void setPtrHandler(View view) {
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                onPtrLoadMoreBegin(frame);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onPtrRefreshBegin(frame);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, null == view ? content : view, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, null == view ? content : view, header);
            }
        });
    }

    /**
     * 上拉加载
     */
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {

    }

    /**
     * 下拉刷新
     */
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {

    }

    /******************************************* TollBar相关 ******************************************************/
    private void initToolBar() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        mToolbarSubTitle.setVisibility(View.GONE);
         /*
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("Sub Title");
        */
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
    }

    /**
     * 获取头部标题的TextView
     *
     * @return toolbar标题
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     *
     * @param title 标题内容
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    public void setToolbarTitleColor(@ColorInt int color) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setTextColor(color);
        } else {
            getToolbar().setTitleTextColor(color);
        }
    }


    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.drawable.ic_back);
        //  返回按钮点击事件
        getToolbar().setNavigationOnClickListener((v) -> onBackPress());
    }


    protected ImageView getRightIv() {
        return (ImageView) findViewById(R.id.iv_right);
    }

    protected void setRightIvRes(@DrawableRes int res) {
        getRightIv().setBackgroundResource(res);
        getRightIv().setVisibility(View.VISIBLE);
    }

    protected void onBackPress() {
        onBackPressed();
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return 是否显示toolbar返回键
     */
    protected boolean isShowBacking() {
        return true;
    }

/******************************************* TollBar相关结束 ******************************************************/

    /**
     * 初始化contentiew
     */
    private void initContentView(@LayoutRes int layoutResID) {
        ViewGroup viewGroup= (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout= (LinearLayout) LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        viewGroup.addView(parentLinearLayout);
    }

    /**
     * 重写setContentView方法，子类Activity会调用该方法
     *
     * @param layoutResID 子类布局文件的id
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //  将子类布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        parentLinearLayout.addView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLinearLayout.addView(view, params);
        ButterKnife.bind(this);
    }

    private void setStatusBarTransparent() {
        //  把状态栏去掉
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //  设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 设置系统标题栏透明
     *
     * @param activity 要设置的Activity
     * @param on       是否透明
     */
    protected void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色
     */
    public void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(this, true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(getResources().getColor(color));
        }
    }


    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            DialogUtils dialogUtils=new DialogUtils(this);
            dialogUtils.showTwoButtonDialog(getString(R.string.label_ok), v -> ActivityCompat.requestPermissions(BaseActivity.this,
                    new String[]{permission}, requestCode), v -> dialogUtils.dismissDialog());

           /* showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));*/
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }
}
