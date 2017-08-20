package com.cypoem.idea.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cypoem.idea.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleActivity extends BaseActivity {

    @BindView(R.id.tv_article)
    TextView tvArticle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setToolbarTitleColor(Color.parseColor("#010101"));
        Intent intent = getIntent();
        tvArticle.setText(intent.getStringExtra("article"));
        setToolBarTitle(intent.getStringExtra("title"));

    }

    @OnClick({R.id.ll_article, R.id.tv_article})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_article:
            case R.id.tv_article:
                onBackPress();
                break;
        }
    }
}
