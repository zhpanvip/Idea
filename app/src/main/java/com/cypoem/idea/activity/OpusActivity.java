package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;

import com.cypoem.idea.R;

public class OpusActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opus;
    }

    @Override
    protected void init() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OpusActivity.class);
        context.startActivity(intent);
    }
}
