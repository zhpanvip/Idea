package com.cypoem.idea;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.BaseRxActivity;
import com.airong.core.utils.ToastUtils;

public class BaseActivity extends BaseRxActivity {
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里
    public ImageView mIvLeft;   //  TitleBar左边图标（返回键）
    public TextView tvTitle;    //  TitleBar标题
    public TextView tvRight;    //  TitleBar右边文字
    public ImageView ivRight;   //  TitleBar右边图标
    public RelativeLayout mRlTitleBar;  //  TitleBar所在的布局
    public RadioGroup mRadioGroup;  //  TitleBar中间部分的单选框 记一笔页面效果
    public RadioButton mRbLeft;     //  左边单选框
    public RadioButton mRbRight;    //  右边单选框

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);
        initView();
        setListener();
    }

    private void setListener() {
        //  返回键监听事件 结束Activity
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtils.show("finish");
                finish();

            }
        });
    }

    private void initView() {
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        mRlTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_title_bar);
        mRbLeft = (RadioButton) findViewById(R.id.rb_left);
        mRbRight = (RadioButton) findViewById(R.id.rb_right);
    }




    /**
     * 初始化contentiew
     */
    private void initContentView(int layoutResID) {
        //  初始化ViewGroup
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        //  移除viewGroup中的所有布局
        viewGroup.removeAllViews();
        //  构造线性布局
        parentLinearLayout = new LinearLayout(this);
        //  设置线性布局垂直排列
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        //  将构造的香型布局添加到viewGroup
        viewGroup.addView(parentLinearLayout);
        //  将BaseActivity的布局添加到parentLinearLayout
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    /**
     * 重写setContentView方法，子类Activity会调用该方法
     * @param layoutResID   子类布局文件的id
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
    private void setTranslucentStatus(Activity activity, boolean on) {
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
}
