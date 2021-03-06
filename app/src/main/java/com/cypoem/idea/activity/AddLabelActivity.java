package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArraySet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLabelActivity extends BaseActivity {

    @BindView(R.id.id_fl)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private String[] mLabels;
    // private String[] labelArray;
    Set<Integer> selectedList;
    private TagAdapter<String> mAdapter;
    private EditText mEtCustom;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_lable;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        int positions = getIntent().getIntExtra("positions", 200);
        selectedList = new ArraySet<>();
        mLabels = getApplicationContext().getResources().getStringArray(R.array.label);
        mAdapter = new TagAdapter<String>(mLabels) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(AddLabelActivity.this).inflate(R.layout.item_label, mFlowLayout, false);
                TextView tv = (TextView) view.findViewById(R.id.tv_label);
                tv.setText(s);
                return tv;
            }
        };

        mFlowLayout.setAdapter(mAdapter);
        // mFlowLayout.setMaxSelectCount(3);
        // mAdapter.setSelectedList(1,2,4);
       /* for (int i = 0; i < positions.length; i++) {
            mAdapter.setSelectedList(Integer.parseInt(positions[i]));
        }*/
       // mAdapter.setSelected(positions, "");
        mFlowLayout.setOnSelectListener((Set<Integer> selectPosSet) -> {
            selectedList = selectPosSet;
        });
        mFlowLayout.setOnTagClickListener((View view, int position, FlowLayout parent) -> {
            if (position == mLabels.length - 1) {
                showCustomDialog();
            }
            return true;
        });


    }

    private void showCustomDialog() {
        View dialog = createDialog(R.layout.input_dialog, true);
        mEtCustom = (EditText) dialog.findViewById(R.id.et_dialog_content);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener((View v) -> {
            String customStr = mEtCustom.getText().toString();
            selectedLabel(customStr, 200);
            dismissDialog();
            finish();
        });
    }


    @Override
    protected void onBackPress() {
        goBack();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddLabelActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.id_fl, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_fl:
                break;
            case R.id.btn_complete:
                selectComplete();
                break;
        }
    }

    private void selectComplete() {
        if (mFlowLayout.getSelectedList().size() == 0) {
            showToast("请选择标签");
        } else {
            setValues();
        }
    }

    private void setValues() {
        String labels = "";
        int positions = 200;
        selectedList = mFlowLayout.getSelectedList();
        for (Integer i : selectedList) {
            labels += mLabels[i];
            positions = i;
        }
        selectedLabel(labels, positions + 3);
        finish();
    }

    private void selectedLabel(String labels, int positions) {
        Intent intent = new Intent();
        intent.putExtra("label", labels);
        intent.putExtra("positions", positions);
        setResult(PublishActivity.SELECT_LABEL, intent);
    }


    private void showDig() {
        showTwoButtonDialog("您还没有选择标签，\n确定要退出吗？", "确定", "取消", (View v) -> {
            dismissDialog();
            Intent intent = new Intent();
            setResult(PublishActivity.SELECT_LABEL, intent);
            finish();
        }, (View v) -> dismissDialog());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        if (mFlowLayout.getSelectedList().size() == 0) {
            showDig();
        } else {
            setValues();
        }
    }
}
