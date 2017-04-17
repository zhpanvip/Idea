package com.cypoem.idea.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airong.core.BaseRxActivity;
import com.cypoem.idea.R;

import java.lang.reflect.Field;

public class BaseActivity extends BaseRxActivity {
    //把父类activity和子类activity的view都add到这里
    private LinearLayout parentLinearLayout;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);
        initToolBar();
    }


    private void initToolBar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
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

/******************************************* TollBar相关 ******************************************************/
    /**
     * 获取头部标题的TextView
     *
     * @return
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
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
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
        getToolbar().setNavigationIcon(R.drawable.icon_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }
/******************************************* TollBar相关结束 ******************************************************/


    /**
     * 初始化contentiew
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        //  将BaseActivity的布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * 重写setContentView方法，子类Activity会调用该方法
     *
     * @param layoutResID 子类布局文件的id
     */
    @Override
    public void setContentView(int layoutResID) {
        //  将子类布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    public void setContentView(View view) {
        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLinearLayout.addView(view, params);
    }

    /**
     * 改变系统状态栏颜色
     *
     * @param activity
     * @param color    color xml文件下的颜色
     */
    public void setStatusBarColor(Activity activity, int color) {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }*/
        if (Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(color);
        }
    }

    /**
     * 设置系统标题栏的透明度
     *
     * @param activity
     * @param on
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


    //  设置魅族手机状态栏字体颜色为深色
    protected static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
