package com.cypoem.idea.activity;

import android.view.View;

import com.cypoem.idea.R;

public class LoginActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
    }


}
