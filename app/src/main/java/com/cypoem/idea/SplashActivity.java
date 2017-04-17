package com.cypoem.idea;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    //  SplashActivity的布局文件
    private RelativeLayout mRelativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //  设置状态栏透明
        setTranslucentStatus(this,true);
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
        getToolbar().setVisibility(View.GONE);
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
                //boolean isFirst = SharedPreferencesUtils.isFirst(SplashActivity.this);
                boolean isFirst=false;
                if(isFirst){    //  如果第一次运行则初始化数据库

                   // SharedPreferencesUtils.setFirst(SplashActivity.this);
                }
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
