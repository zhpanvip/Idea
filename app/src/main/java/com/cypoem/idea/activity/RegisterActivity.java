package com.cypoem.idea.activity;

import android.view.View;

import com.cypoem.idea.R;

public class RegisterActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
    }
}
