package com.cypoem.idea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airong.core.dialog.DialogUtils;
import com.cypoem.idea.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DescribeActivity extends BaseActivity {

    @BindView(R.id.et_describe)
    EditText etDescribe;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_describe;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String describe = intent.getStringExtra("describe");
        etDescribe.setText(describe);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String describe = etDescribe.getText().toString();
        if (TextUtils.isEmpty(describe)) {
            showToast("请输入作品描述");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("describe", describe);
        setResult(PublishActivity.ADD_DESCRIBE, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        String describe = etDescribe.getText().toString();
        if (!TextUtils.isEmpty(describe)) {
            showDig();
        } else {
            finish();
        }
    }

    @Override
    protected void onBackPress() {
        goBack();
    }

    private void showDig() {
        DialogUtils dialogUtils = new DialogUtils(this);
        dialogUtils.showTwoButtonDialog("返回后作品描述将不会被保存，\n确定要返回吗？", (View v) -> {
            dialogUtils.dismissDialog();
            finish();
        }, (View v) -> dialogUtils.dismissDialog());
    }
}
