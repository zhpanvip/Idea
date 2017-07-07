package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/*import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;*/

public class LoginActivity extends BaseActivity /*implements Callback, PlatformActionListener*/ {
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR= 4;
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
                break;
            case R.id.iv_qq:
               // authorize(new QQ(this));
                break;
            case R.id.iv_weChat:
               // authorize(new Wechat(this));
                showToast("wechat");
                break;
            case R.id.iv_weiBo:
               // authorize(new SinaWeibo(this));
                break;
            case R.id.tv_new_user:
                //RegisterActivity.start(this);
                SetPasswordActivity.start(this,"17602150874");
                break;
            case R.id.tv_login_error:
                break;

        }
    }


   /* private void authorize(Platform plat) {
        if(plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(true);
        plat.showUser(null);
    }*/



    /*private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }*/

   /* public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
    }*/

   /* public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }*/

    /*public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }*/

    private void login() {
        String password = etPassword.getText().toString().trim();
        String phone = etUsername.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            showToast("请输入手机号");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            showToast("请输入密码");
            return;
        }
        postData(phone,password);
    }

    private void postData(String phone,String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        IdeaApi.getApiService()
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this,true) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        loginSuccess(response.getResult());
                    }
                });
    }

    private void loginSuccess(UserBean result) {
        UserInfoTools.setIsLogin(this,true);
        UserInfoTools.setUser(this,result);
        showToast("登录成功");
        finish();
    }


    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_USERID_FOUND: {
                showToast(R.string.userid_found);
            }
            break;
            case MSG_LOGIN: {

                String text = getString(R.string.logining, msg.obj);
                showToast(R.string.auth_cancel);
                System.out.println("---------------");

//				Builder builder = new Builder(this);
//				builder.setTitle(R.string.if_register_needed);
//				builder.setMessage(R.string.after_auth);
//				builder.setPositiveButton(R.string.ok, null);
//				builder.create().show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                showToast(R.string.auth_cancel);
                System.out.println("-------MSG_AUTH_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                showToast(R.string.auth_error);
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                showToast(R.string.auth_complete);
                System.out.println("--------MSG_AUTH_COMPLETE-------");
            }
            break;
        }
        return false;
    }

    public void onDestroy() {
       // ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    /*private void login() {
        if(TextUtils.isEmpty(etUsername.getText().toString())){
            return;
        }else if(TextUtils.isEmpty(etPassword.getText().toString())){
            return;
        }
        UserInfoTools.setIsLogin(this,true);
        finish();
    }*/
}
