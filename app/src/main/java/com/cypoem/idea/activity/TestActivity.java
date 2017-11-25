package com.cypoem.idea.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cypoem.idea.R;

public class TestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getToolbar().setVisibility(View.GONE);
        setTranslucentStatus(this,true);
        setStatusBarColor(R.color.colorPrimary);
    }

    public static void start(Context context){
        Intent intent=new Intent(context,TestActivity.class);
        context.startActivity(intent);
    }
}
