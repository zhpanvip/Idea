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

import com.airong.core.dialog.DialogUtils;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.RegexUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.event.UpdatePswEvent;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.google.gson.Gson;
import com.mob.MobSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetIdentifyCodeActivity extends BaseActivity {
    protected static final int FORGET_PSW = 1;
    protected static final int REGISTER = 0;
    private DialogUtils dialogUtils;

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

    private final static long ANIMATION_DURATION = 60000;
    private final static long SECOND = 1000;
    private static final int FAIL = 10;   //  验证码获取/提交异常

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static final String APPKEY = "1d348fde5cbd8";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static final String APPSECRET = "48ae381a929cb0f90bf196a54cccb1f2";

    private WeakHandler handle = new WeakHandler(new WeakReference<>(this));
    private static int type;

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
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        dialogUtils=new DialogUtils(this);
        initData();
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
        EventBus.getDefault().register(this);
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

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, GetIdentifyCodeActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        EventBus.getDefault().unregister(this);
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
            showToast(R.string.phone_can_not_null);
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(R.string.input_correct_phone_num);
            return;
        }
        String code = mEtIdentifyCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            showToast(R.string.input_identify_code);
            return;
        }
        dialogUtils.showProgress();
        //showProgress(this);
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    //  验证手机号是否注册
    private void confirmIsRegistered() {
        IdeaApi.getApiService()
                .isRegistered(phone)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        boolean isRegistered = (boolean) response.getResult();
                        if(type==REGISTER){
                            showToast("账号已经注册过了哦，请登陆。");
                        }
                       if(type == FORGET_PSW&&isRegistered){
                            SMSSDK.getVerificationCode("86", phone, null);
                        }
                    }

                    @Override
                    public void onFail(BasicResponse response, int code) {
                        //  账号未注册
                        if (type == REGISTER&&code == 202)
                            SMSSDK.getVerificationCode("86", phone, null);
                    }
                });
    }

    //  获取获取验证码
    public void getVerificationCode() {
        phone = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast(R.string.phone_can_not_null);
            return;
        }

        if (RegexUtils.isMobileExact(phone)) {
            confirmIsRegistered();
        } else {
            showToast(R.string.input_correct_phone_num);
        }
    }

    //  避免内存泄漏
    private static class WeakHandler extends Handler {
        private WeakReference<GetIdentifyCodeActivity> activity;

        WeakHandler(WeakReference<GetIdentifyCodeActivity> activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            GetIdentifyCodeActivity activity = this.activity.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:// 验证码提交成功
                    activity.dialogUtils.dismissProgress();
                    if (type == FORGET_PSW) {
                        UpdatePswActivity.start(activity, UpdatePswActivity.FORGET_PSW);
                    } else if (type == REGISTER) {
                        SetPasswordActivity.start(activity, activity.phone);
                        activity.finish();
                    }

                    break;
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:// 验证码获取成功
                    activity.showToast(R.string.send_vertify_code_success);
                    activity.countDown();
                    activity.dialogUtils.dismissProgress();
                    break;
                case FAIL:
                    String message = ((Throwable) msg.obj).getMessage();
                    Gson gson = new Gson();
                    MobResultBean mobResultBean = gson.fromJson(message, MobResultBean.class);
                    activity.showToast(mobResultBean.getDetail());
                    activity.dialogUtils.dismissProgress();
                    break;
            }
        }
    }


    /**
     * 接收注册成功事件
     *
     * @param registerSuccess
     */
    @Subscribe
    public void registerSuccess(CompleteRegisterActivity.RegisterSuccess registerSuccess) {
        LogUtils.e(registerSuccess.msg);
        finish();
    }

    /**
     * 接收修改密码成功事件
     *
     * @param event
     */
    @Subscribe
    public void updatePswSuccess(UpdatePswEvent event) {
        finish();
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
