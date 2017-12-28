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

import com.airong.core.dialog.DialogUtils;
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

    private String[] mLabels;
    // private String[] labelArray;
    Set<Integer> selectedList;
    private int selectedPosition;
    private TagAdapter<String> mAdapter;
    private EditText mEtCustom;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_lable;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSubTitle().setText("完成");
        getSubTitle().setTextColor(getResources().getColor(R.color.text_theme));
        getSubTitle().setVisibility(View.VISIBLE);
        selectedPosition = getIntent().getIntExtra("selectedPosition", 200);
        selectedList = new ArraySet<>();
        mLabels = getApplicationContext().getResources().getStringArray(R.array.label);
        mAdapter = new TagAdapter<String>(mLabels) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(AddLabelActivity.this).inflate(R.layout.item_label, mFlowLayout, false);
                TextView tv =  view.findViewById(R.id.tv_label);
                tv.setText(s);
                return tv;
            }
        };


        mFlowLayout.setAdapter(mAdapter);
        if(selectedPosition!=-1)
        mAdapter.setSelectedList(selectedPosition);
        mFlowLayout.setOnSelectListener((Set<Integer> selectPosSet) -> {
            if(selectPosSet.size()==0){
                selectedPosition=-1;
                return;
            }
            for (int i : selectPosSet)
                selectedPosition = i;
        });
    }

    public void selfLabel(View view) {
        showCustomDialog();
    }

    private void showCustomDialog() {
        DialogUtils dialogUtils = new DialogUtils(this);
        View dialog = dialogUtils.createDialog(R.layout.input_dialog, true);
        mEtCustom =  dialog.findViewById(R.id.et_dialog_content);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener((View v) -> {
            String customStr = mEtCustom.getText().toString();
            selectedLabel(customStr);
            dialogUtils.dismissDialog();
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

    @OnClick({R.id.id_fl, R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_fl:
                break;
            case R.id.toolbar_subtitle:
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
        selectedList = mFlowLayout.getSelectedList();
        for (Integer i : selectedList) {
            labels += mLabels[i];
        }
        selectedLabel(labels);
        finish();
    }

    private void selectedLabel(String labels) {
        Intent intent = new Intent();
        intent.putExtra("label", labels);
        intent.putExtra("selectedPosition", selectedPosition);
        setResult(PublishActivity.SELECT_LABEL, intent);
    }

    private void showDig() {
        DialogUtils dialogUtils = new DialogUtils(this);
        dialogUtils.showTwoButtonDialog("您还没有选择标签，\n确定要退出吗？", (View v) -> {
            dialogUtils.dismissDialog();
            Intent intent = new Intent();
            setResult(PublishActivity.SELECT_LABEL, intent);
            finish();
        }, (View v) -> dialogUtils.dismissDialog());
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
