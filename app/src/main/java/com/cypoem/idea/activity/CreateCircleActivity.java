package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cypoem.idea.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateCircleActivity extends BaseActivity {

    @BindView(R.id.btn_save)
    TextView mBtnSave;
    @BindView(R.id.iv_circle_icon)
    ImageView mIvCircleIcon;
    @BindView(R.id.ll_update_icon)
    LinearLayout mLlUpdateIcon;
    @BindView(R.id.rv_icon)
    RecyclerView mRvIcon;
    @BindView(R.id.et_circle_name)
    EditText mEtCircleName;
    @BindView(R.id.rl_label)
    RelativeLayout mRlLabel;
    @BindView(R.id.tv_public)
    TextView mTvPublic;
    @BindView(R.id.tv_private)
    TextView mTvPrivate;
    @BindView(R.id.rl_type)
    RelativeLayout mRlType;
    @BindView(R.id.et_introduce)
    EditText mEtIntroduce;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateCircleActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_save, R.id.ll_update_icon, R.id.rl_label, R.id.tv_public, R.id.tv_private})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                break;
            case R.id.ll_update_icon:
                break;
            case R.id.rl_label:
                break;
            case R.id.tv_public:
                break;
            case R.id.tv_private:
                break;
        }
    }
}
