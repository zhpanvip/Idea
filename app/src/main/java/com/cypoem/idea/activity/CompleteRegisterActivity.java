package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.cypoem.idea.R;
import com.cypoem.idea.view.SexView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteRegisterActivity extends BaseActivity {

    @BindView(R.id.et_pen_name)
    EditText mEtPenName;
    @BindView(R.id.et_sign)
    EditText mEtSign;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.sex_view)
    SexView mSexView;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_regist;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
    }

    private void setListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSexView.setMalePercent(1-progress/100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CompleteRegisterActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
       finish();
    }
}
