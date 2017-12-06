package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airong.core.utils.BarUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.utils.SharedPreferencesHelper;
import com.zhpan.viewpager.holder.ViewHolder;
import com.zhpan.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    private List<Integer> mListInt = new ArrayList<>();

    @BindView(R.id.vp_welcome)
    CircleViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.StatusBarLightMode(this);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }

    protected void init() {
        for (int i = 1; i <= 4; i++) {
            mListInt.add(R.layout.welcome_pager2);
        }
        mViewPager.setAutoPlay(false);
        mViewPager.setCanLoop(false);
        mViewPager.setIndicatorRadius(5);
        mViewPager.setIndicatorColor(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
        mViewPager.setPages(mListInt, () -> new MViewHolder());
    }

    public static class MViewHolder implements ViewHolder<Integer> {
        private ImageView mIvBackground;
        private Button mButton;
        private ImageView mIvDot;

        @Override
        public View createView(Context context, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.welcome_pager2, null);
            mIvBackground = view.findViewById(R.id.iv_welcome);
            mButton = view.findViewById(R.id.btn_welcome);
            mIvDot = view.findViewById(R.id.iv_dot);
            return view;
        }

        @Override
        public void onBind(Context context, Integer integer, int position, int size) {
            int drawable = context.getResources().getIdentifier("page" + (position + 1), "drawable", context.getPackageName());
            mIvBackground.setBackgroundResource(drawable);
            if (position == 3) {
                mIvDot.setVisibility(View.VISIBLE);
                mButton.setVisibility(View.VISIBLE);
                mButton.setOnClickListener(v -> {
                    MainActivity.start(context);
                    if (context instanceof WelcomeActivity) {
                        SharedPreferencesHelper.put(context, "firstRunFlag", false);
                        ((WelcomeActivity) context).finish();
                    }
                });
            }
        }
    }
}
