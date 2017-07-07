package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.post_bean.AdvicePost;
import com.cypoem.idea.module.post_bean.RegisterPost;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SuggestActivity extends BaseActivity {

    @BindView(R.id.et_advice)
    EditText mEtAdvice;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private String advice;
    private String phone;
    private String fromWhere;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void init(Bundle savedInstanceState) {


        Intent intent = getIntent();
        fromWhere = intent.getStringExtra("fromWhere");
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        advice = mEtAdvice.getText().toString();
        phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(advice)) {
            showToast("请填写遇到的问题或建议");
            return;
        }
        postAdvice();
    }

    private void postAdvice() {
        Map<String, String> adviceMap = new HashMap<>();
        adviceMap.put("phone", phone);
        adviceMap.put("suggestion", advice);
        adviceMap.put("interface_source", fromWhere);
        adviceMap.put("user_id", UserInfoTools.getUser(this).getUid());
        adviceMap.put("user_type", "1");
        IdeaApi.getApiService()
                .postAdvice(adviceMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        showToast(response.getResult());
                        finish();
                    }
                });
    }

    public static void start(Context context, String fromWhere) {
        Intent intent = new Intent(context, SuggestActivity.class);
        intent.putExtra("fromWhere", fromWhere);
        context.startActivity(intent);
    }
}
