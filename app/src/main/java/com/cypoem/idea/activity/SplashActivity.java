package com.cypoem.idea.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cypoem.idea.R;
import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.ll_splash)
    LinearLayout mIvSplash;
    @BindView(R.id.btn_count_down)
    TextView mButton;
    //private static final float SCALE_END = 1.3F;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_publish)
    TextView mTvPublish;
    private final static long ANIMATION_DURATION = 5000;
    private final static long SECOND=1000;
    private boolean isGo2Main=true;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_everyday;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getToolbar().setVisibility(View.GONE);
        mButton.setVisibility(View.VISIBLE);
        //  给启动页面设置动画
        setAnimation();
       // finishActivity();
        setCountDown();
    }

    private void setCountDown() {
        CountDownTimer countDownTimer=new CountDownTimer(ANIMATION_DURATION,SECOND){
            @Override
            public void onTick(long millisUntilFinished) {
                mButton.setText("跳过"+millisUntilFinished/SECOND+"s");
            }

            @Override
            public void onFinish() {
                mButton.setText("跳过"+0+"s");
                if(isGo2Main)
                goToMain();
            }
        };
        countDownTimer.start();
    }

    //  启动透明渐变动画
    private void setAnimation() {
        // ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        // ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

       // AnimatorSet set = new AnimatorSet();
        //  set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        // set.start();
        /*set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                MainActivity.start(SplashActivity.this);
                SplashActivity.this.finish();
            }
        });*/
    }

    //  splash界面休眠3秒后销毁
   /* private void finishActivity() {
        Observable.timer(ANIMATION_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((@NonNull Long aLong) -> {
                    if(isGo2Main)
                        goToMain();

                });
    }*/

    private void goToMain() {
        MainActivity.start(SplashActivity.this);
        overridePendingTransition(0, android.R.anim.fade_out);
        finish();
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK||super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.btn_count_down, R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_count_down:
                isGo2Main=false;
               goToMain();
                break;
            case R.id.tv_publish:
                isGo2Main=false;
                CreateEveryDayActivity.start(this);
                finish();
                break;
        }
    }
}
