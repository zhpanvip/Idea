package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.view.MaxByteLengthEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateEveryDayActivity extends BaseActivity {

    @BindView(R.id.iv_add_pic)
    ImageView mIvAddPic;
    @BindView(R.id.et_sentence1)
    MaxByteLengthEditText mEtSentence1;
    @BindView(R.id.et_sentence2)
    MaxByteLengthEditText mEtSentence2;
    @BindView(R.id.et_pen_name)
    MaxByteLengthEditText mEtPenName;
    @BindView(R.id.tv_preview)
    TextView mTvPreview;
    @BindView(R.id.tv_publish)
    TextView mTvPublish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_every_day;
    }

    @Override
    protected void init() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateEveryDayActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_add_pic, R.id.tv_preview, R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_pic:
                break;
            case R.id.tv_preview:
                PreviewActivity.start(this);
                break;
            case R.id.tv_publish:
                MainActivity.start(this);
                finish();
                break;
        }
    }
}
