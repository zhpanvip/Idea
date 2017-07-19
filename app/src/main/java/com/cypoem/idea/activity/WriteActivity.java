package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.cypoem.idea.R;
import butterknife.BindView;

public class WriteActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_rewrite)
    TextView tvRewrite;
    @BindView(R.id.tb_rewrite)
    ToggleButton tbRewrite;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setData();
    }

    private void setData() {
        getSubTitle().setText("保存草稿");
        getSubTitle().setVisibility(View.VISIBLE);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WriteActivity.class);
        context.startActivity(intent);
    }
}
