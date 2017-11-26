package com.cypoem.idea.activity;

import android.os.Bundle;

import com.cypoem.idea.R;

public class NearbyActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setRefreshLayout(true);
    }
}
