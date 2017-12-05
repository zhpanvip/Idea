package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.airong.core.utils.BarUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.WelcomePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {


    @BindView(R.id.vp_welcome)
    ViewPager mViewPager;

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
        int[] layouts = {R.layout.welcome_pager1, R.layout.welcome_pager1, R.layout.welcome_pager1, R.layout.welcome_pager2};
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(this, layouts);
        mViewPager.setAdapter(adapter);
    }
}
