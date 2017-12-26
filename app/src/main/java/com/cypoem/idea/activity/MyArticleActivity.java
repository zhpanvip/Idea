package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cypoem.idea.R;

public class MyArticleActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MyArticleActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
}
