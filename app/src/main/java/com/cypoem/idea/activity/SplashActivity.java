package com.cypoem.idea.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cypoem.idea.R;
import com.cypoem.idea.view.CountDownButton;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.ll_splash)
    LinearLayout mIvSplash;
    @BindView(R.id.btn_count_down)
    CountDownButton mButton;
    //private static final float SCALE_END = 1.3F;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_publish)
    TextView mTvPublish;
    private long ANIMATION_DURATION = 5000;
    private boolean isGo2Main=true;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_everyday;
    }

    @Override
    protected void init() {
        getToolbar().setVisibility(View.GONE);
        mButton.setVisibility(View.VISIBLE);
        //  给启动页面设置动画
        setAnimation();
        finishActivity();
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
    private void finishActivity() {
        Observable.timer(ANIMATION_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((@NonNull Long aLong) -> {
                    if(isGo2Main)
                    MainActivity.start(SplashActivity.this);
                    overridePendingTransition(0, android.R.anim.fade_out);
                    finish();
                });
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
                MainActivity.start(this);
                finish();
                break;
            case R.id.tv_publish:
                isGo2Main=false;
                CreateEveryDayActivity.start(this);
                finish();
                break;
        }
    }
}
