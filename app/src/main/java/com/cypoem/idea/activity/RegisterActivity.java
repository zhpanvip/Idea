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
import com.cypoem.idea.view.CountDownButton;
import com.mob.MobSDK;
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

    private final static long ANIMATION_DURATION = 50000;
    private final static long SECOND=1000;

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "1e41285782564";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "e989c7f12c5b645b5235325ed8f35593";
    private EventHandler eventHandler;
    private boolean boolShowInDialog=false;
    private String phone;

  Handler handle=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          switch (msg.what){
              case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:// 验证码提交成功

                  break;
              case SMSSDK.EVENT_GET_VERIFICATION_CODE:// 验证码获取成功
                    showToast("验证码已发送至您的手机");
                    countDown();
                  break;

          }
      }
  };
    //  验证码倒计时
    private void countDown() {
        tvGetCode.setEnabled(false);
        CountDownTimer timer=new CountDownTimer(ANIMATION_DURATION,SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText(millisUntilFinished/1000+"秒后重新获取");
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

        MobSDK.init(this,APPKEY,APPSECRET);

         eventHandler=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        SetPasswordActivity.start(RegisterActivity.this);
                        finish();
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Message msg=new Message();
                        msg.what=SMSSDK.EVENT_GET_VERIFICATION_CODE;
                        handle.sendMessage(msg);
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    showToast(((Throwable) data).getMessage());
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
                SMSSDK.submitVerificationCode("86",phone, mEtIdentifyCode.getText().toString().trim());
                break;
        }
    }

    //  获取获取验证码
    public void getVerificationCode() {
        phone = etUsername.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            showToast("手机号不能为空");
            return;
        }
        if(RegexUtils.isMobileExact(phone)){
            SMSSDK.getVerificationCode("86",phone, null);
        }else {
            showToast("请输入正确的手机号");
        }
    }
}
