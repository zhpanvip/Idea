package com.cypoem.idea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cypoem.idea.R;
import com.cypoem.idea.view.MaxByteLengthEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {

    @BindView(R.id.edit)
    MaxByteLengthEditText mEditText;
    private int type;
    private String what;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }


    private void initData() {
        Intent intent = getIntent();
        what = intent.getStringExtra("what");
        type = intent.getIntExtra("type", 0);
        getToolbarTitle().setText("修改" + what);

        mEditText.setHint("请输入" + what);
        getSubTitle().setText("确定");
        getSubTitle().setVisibility(View.VISIBLE);
        setEditText(type);
    }

    private void setEditText(int type) {
        switch (type){
            case EditInfoActivity.SIGN:
                mEditText.setMaxByteLength(60);
                break;
            case EditInfoActivity.PEN_NAME:
                mEditText.setMaxByteLength(16);
                break;
            case EditInfoActivity.INTRODUCE:
                mEditText.setMaxByteLength(400);
                break;
        }
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                saveData();
                break;
        }
    }

    private void saveData() {
        String content = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请输入" + what);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("result", mEditText.getText().toString());
        setResult(type, intent);
        finish();
    }
}
