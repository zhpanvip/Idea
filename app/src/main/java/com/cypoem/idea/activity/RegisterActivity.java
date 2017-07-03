package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airong.core.utils.RegexUtils;
import com.cypoem.idea.R;
import com.google.gson.Gson;
import com.mob.MobSDK;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_identify_code)
    EditText mEtIdentifyCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    private EventHandler eventHandler;
    private String phone;

    private final static long ANIMATION_DURATION = 50000;
    private final static long SECOND = 1000;
    private static final int FAIL = 10;   //  验证码获取/提交异常

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static final String APPKEY = "1e41285782564";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static final String APPSECRET = "e989c7f12c5b645b5235325ed8f35593";

    private WeakHandler handle = new WeakHandler(new WeakReference<>(this));

    //  验证码倒计时
    private void countDown() {
        tvGetCode.setEnabled(false);
        CountDownTimer timer = new CountDownTimer(ANIMATION_DURATION, SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText(millisUntilFinished / 1000 + "秒后重新获取");
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("获取验证码");
                tvGetCode.setEnabled(true);
            }
        };

        timer.start();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
        initMob();
    }

    private void initMob() {
        MobSDK.init(this, APPKEY, APPSECRET);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                if (result == SMSSDK.RESULT_COMPLETE) {

                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        msg.what = SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE;
                        handle.sendMessage(msg);


                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        msg.what = SMSSDK.EVENT_GET_VERIFICATION_CODE;
                        handle.sendMessage(msg);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    msg.what = FAIL;
                    msg.obj = data;
                    handle.sendMessage(msg);
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }


    @OnClick({R.id.tv_get_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getVerificationCode();
                break;
            case R.id.btn_next:
                verificationCode();
                break;
        }
    }

    private void verificationCode() {
        phone = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast("请输入正确的手机号");
            return;
        }
        String code = mEtIdentifyCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }
        showProgress(this);
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    //  获取获取验证码
    public void getVerificationCode() {
        showProgress(this);
        phone = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (RegexUtils.isMobileExact(phone)) {
            SMSSDK.getVerificationCode("86", phone, null);
        } else {
            showToast("请输入正确的手机号");
        }
    }


    private static class WeakHandler extends Handler {
        private WeakReference<RegisterActivity> activity;

        WeakHandler(WeakReference<RegisterActivity> activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity registerActivity = activity.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:// 验证码提交成功
                    registerActivity.dismissProgress();
                    SetPasswordActivity.start(registerActivity,registerActivity.phone);
                    registerActivity.finish();
                    break;
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:// 验证码获取成功
                    registerActivity.showToast("验证码已发送至您的手机");
                    registerActivity.countDown();
                    registerActivity.dismissProgress();
                    break;
                case FAIL:
                    String message = ((Throwable) msg.obj).getMessage();
                    Gson gson = new Gson();
                    MobResultBean mobResultBean = gson.fromJson(message, MobResultBean.class);
                    registerActivity.showToast(mobResultBean.getDetail());
                    registerActivity.dismissProgress();
                    break;
            }
        }
    }

    static class MobResultBean {
        private String status;
        private String detail;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
