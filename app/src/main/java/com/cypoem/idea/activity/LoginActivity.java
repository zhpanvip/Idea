package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.mob.tools.utils.UIHandler;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, PlatformActionListener {
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget_psw)
    TextView tvForgetPsw;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.iv_weChat)
    ImageView ivWeChat;
    @BindView(R.id.iv_weiBo)
    ImageView ivWeiBo;
    @BindView(R.id.tv_new_user)
    TextView tvNewUser;
    @BindView(R.id.tv_login_error)
    TextView tvLoginError;
    private String phone;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        getToolbar().setVisibility(View.GONE);
        EventBus.getDefault().register(this);
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_login, R.id.tv_forget_psw, R.id.iv_qq, R.id.iv_weChat, R.id.iv_weiBo, R.id.tv_new_user, R.id.tv_login_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_forget_psw:
                GetIdentifyCodeActivity.start(this, GetIdentifyCodeActivity.FORGET_PSW);
                break;
            case R.id.iv_qq:
                authorize(new QQ(), 2);
                break;
            case R.id.iv_weChat:
                authorize(new Wechat(), 1);
                break;
            case R.id.iv_weiBo:
                authorize(new SinaWeibo(), 3);
                break;
            case R.id.tv_new_user:
              //  GetIdentifyCodeActivity.start(this, GetIdentifyCodeActivity.REGISTER);
                SetPasswordActivity.start(this, "17602150876");
                break;
            case R.id.tv_login_error:
                goProtocol();
                break;
        }
    }

    //  协议相关
    private void goProtocol() {
        String url;
        if (UserInfoTools.isNightMode(this)) {
            url = Constants.PROTOCOL_NIGHT;
        } else {
            url = Constants.PROTOCOL;
        }
        BasicWebViewActivity.start(this, "协议相关", url);
    }

    private void login() {
        String password = etPassword.getText().toString().trim();
        phone = etUsername.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入密码");
            return;
        }
        postData(phone, password);
    }

    private void postData(String phone, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        IdeaApi.getApiService()
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        MobclickAgent.onProfileSignIn(phone);
                        loginSuccess(response.getResult());
                    }
                });
    }

    private void loginSuccess(UserBean result) {
        showToast("登录成功");
        UserInfoTools.setIsLogin(this, true);
        UserInfoTools.setUser(this, result);
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if (action == Platform.ACTION_USER_INFOR) {
            //登录成功,获取需要的信息
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            // login(platform.getName(), platform.getDb().getUserId(), res);
            String openid = platform.getDb().getUserId() + "";
            String gender = platform.getDb().getUserGender();
            String head_url = platform.getDb().getUserIcon();
            String nickname = platform.getDb().getUserName();
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    @Subscribe
    public void registerSuccess(CompleteRegisterActivity.RegisterSuccess registerSuccess) {
        LogUtils.e(registerSuccess.msg);
        finish();
    }

    private void authorize(Platform plat, int type) {
        if (plat == null) {
            popupOthers();
            return;
        }
        if (plat.isClientValid()) {

        } else {

        }
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                thirdPartLogin(plat.getName(), userId, type);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }

    private void popupOthers() {

    }

    private void thirdPartLogin(String name, String userId, int type) {
        IdeaApi.getApiService()
                .thirdPartLogin(name, userId, String.valueOf(type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this, true) {
                    @Override
                    public void onFail(BasicResponse<UserBean> response, int code) {
                        //super.onFail(response, code);
                        if (code == 203) {
                            showToast(response.getMsg());
                        }
                    }

                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        loginSuccess(response.getResult());
                        MobclickAgent.onProfileSignIn(userId);
                    }
                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                showToast(R.string.userid_found);
            }
            break;
            case MSG_LOGIN: {
                String text = getString(R.string.logining, msg.obj);
                showToast(text);
            }
            break;
            case MSG_AUTH_CANCEL: {
                showToast(R.string.auth_cancel);
            }
            break;
            case MSG_AUTH_ERROR: {
                showToast(R.string.auth_error);
            }
            break;
            case MSG_AUTH_COMPLETE: {
                showToast(R.string.auth_complete);
            }
            break;
        }
        return false;
    }
}
