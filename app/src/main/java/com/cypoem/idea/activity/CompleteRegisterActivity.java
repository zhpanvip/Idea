package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.post_bean.RegisterPost;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.view.SexView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String phone;
    String password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_regist;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
    }

    private void setListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSexView.setMalePercent(1 - progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, CompleteRegisterActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        RegisterPost registerPost=new RegisterPost();
        registerPost.setPassword(password);
        registerPost.setPhone(phone);
        IdeaApi.getApiService()
                .register(registerPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<RegisterBean>(this, true) {
                    @Override
                    public void onSuccess(RegisterBean response) {
                        Toast.makeText(CompleteRegisterActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
