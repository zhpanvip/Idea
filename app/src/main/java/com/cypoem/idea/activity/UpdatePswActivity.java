package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cypoem.idea.R;
import com.cypoem.idea.event.UpdatePswEvent;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePswActivity extends BaseActivity {
    protected static final int FORGET_PSW =1;
    protected static final int UPDATE_PSW =0;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_psw;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        if(type==FORGET_PSW){
            etOldPassword.setVisibility(View.GONE);
        }
    }



    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        postComment();
    }


    private void postComment() {
        String oldPsw = etOldPassword.getText().toString().trim();
        String newPsw=etPassword.getText().toString().trim();
        String confirmPsw = etConfirmPassword.getText().toString().trim();
       if (type==UPDATE_PSW&&TextUtils.isEmpty(oldPsw)) {
            showToast("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newPsw)) {
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPsw)) {
            showToast("请确认密码");
            return;
        }

        if(!newPsw.equals(confirmPsw)){
            showToast("两次密码输入不一致");
            return;
        }
        IdeaApi.getApiService()
                .updatePsw(UserInfoTools.getPhone(this),newPsw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        showToast("密码修改成功");
                        EventBus.getDefault().post(new UpdatePswEvent());
                        finish();
                    }
                });
    }


    public static void start(Context context,int type) {
        Intent intent = new Intent(context, UpdatePswActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
}
