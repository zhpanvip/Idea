package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_passwrod;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getToolbar().setVisibility(View.GONE);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
    }

    public static void start(Context context,String phone) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }


    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if(TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPassword)){
            showToast(R.string.password_can_not_empty);
            return;
        }
        if(password.length()<6){
            showToast(R.string.password_length_must_more_than_6);
            return;
        }
        if(!password.equals(confirmPassword)){
            showToast(R.string.password_not_same);
            return;
        }

        CompleteRegisterActivity.start(this,phone,password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void registerSuccess(CompleteRegisterActivity.RegisterSuccess registerSuccess){
        LogUtils.e(registerSuccess.msg);
        finish();
    }
}
