package com.cypoem.idea.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.cypoem.idea.R;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private static final float SCALE_END = 1.3F;
    private long ANIMATION_DURATION = 3000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        getToolbar().setVisibility(View.GONE);

        //  给启动页面设置动画
        setAnimation();
        finishActivity();
    }

    //  启动透明渐变动画
    private void setAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        /*set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                boolean isFirst=false;
                if(isFirst){    //  如果第一次运行则初始化数据库

                    // SharedPreferencesUtils.setFirst(SplashActivity.this);
                }

                MainActivity.start(SplashActivity.this);
                SplashActivity.this.finish();
            }
        });*/

    }

    private void finishActivity() {
        getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<Long> getObservable() {
        return Observable.timer(ANIMATION_DURATION, TimeUnit.MILLISECONDS);
    }

    private Observer<Long> getObserver() {

        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                boolean isFirst = false;
                if (isFirst) {    //  如果第一次运行则初始化数据库
                    // SharedPreferencesUtils.setFirst(SplashActivity.this);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                MainActivity.start(SplashActivity.this);
                SplashActivity.this.finish();
            }
        };
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
