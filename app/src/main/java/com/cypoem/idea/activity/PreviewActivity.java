package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cypoem.idea.R;

/**
 * 预览
 */
public class PreviewActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PreviewActivity.class);
        context.startActivity(intent);
    }
}
