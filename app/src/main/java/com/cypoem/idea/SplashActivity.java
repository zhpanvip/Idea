package com.cypoem.idea;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    //  SplashActivity的布局文件
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  设置状态栏透明
        setStatusBarTransparent();
        //  设置状态栏字体颜色为深色，只在魅族手机上起作用
        setMeizuStatusBarDarkIcon(this,true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //  初始化View（findViewById）
        initView();
        //  设置Activity停留3秒后跳转
        timerControl();
        //  给启动页面设置透明渐变的动画
        setAnimation();
    }

    private void initView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_splash);
    }

    //  设置魅族手机状态栏字体颜色为深色
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
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

    //  设置状态栏为透明
    private void setStatusBarTransparent() {
        //  把状态栏去掉
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //  设置状态栏透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    //  启动透明渐变动画
    private void setAnimation() {
        AlphaAnimation animation=new AlphaAnimation(0f,1f);
        animation.setDuration(3000);
        mRelativeLayout.setAnimation(animation);
    }

    //  启动延迟3秒后跳转到MainActivity
    private void timerControl(){
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                //  从SharedPreferences中查询是否是第一次运行程序
                /*boolean isFirst = SharedPreferencesUtils.isFirst(SplashActivity.this);

                if(isFirst){    //  如果第一次运行则初始化数据库
                    InitData.initUser();    //  初始化用户
                    InitData.initGainClass();   //  初始化记账类别（收入）
                    InitData.initPayClassData();    //  初始化记账类别（支出）
                    InitData.initMember();  //  初始化记一笔页面成员
                    InitData.initPay(); //  初始化记一笔页面支付方式
                    //  把不是第一次运行的标志放到SharedPreferences中
                    SharedPreferencesUtils.setFirst(SplashActivity.this);
                }*/
                //  跳转到MainActivity
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                //  结束这个Activity
                finish();
            }
        };
        timer.schedule(task,3000);
    }

    /**
     * 屏蔽返回键，在这个页面点击返回键无效
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
