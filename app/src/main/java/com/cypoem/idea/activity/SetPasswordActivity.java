package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.UserBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
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
            showToast("密码不能为空");
            return;
        }
        if(!password.equals(confirmPassword)){
            showToast("两次密码输入不一致");
            return;
        }

        CompleteRegisterActivity.start(this,phone,password);
    }
}
