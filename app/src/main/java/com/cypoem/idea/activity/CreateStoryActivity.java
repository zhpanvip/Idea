package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.yalantis.ucrop.UCrop;

import butterknife.BindView;

public class CreateStoryActivity extends BaseActivity {
    @BindView(R.id.ll_label)
    LinearLayout mLlLabel;
    @BindView(R.id.pic_switch)
    SwitchCompat mSwitchCompat;
    @BindView(R.id.rl_add_pic)
    RelativeLayout mRlAddPic;

    public static final int SELECT_LABEL = 256;
    public static final int ADD_DESCRIBE = 257;
    private int type = 200;
    private String label = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_story;
    }

    public void selectLabel(View view) {
        Intent intent = new Intent(this, AddLabelActivity.class);
        intent.putExtra("positions", type);
        startActivityForResult(intent, SELECT_LABEL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
               // clipSuccess(requestCode, data);
                break;
            case UCrop.RESULT_ERROR:
              //  handleCropError(data);
                break;
            case SELECT_LABEL:
                setLabel(data);
                break;
            case ADD_DESCRIBE:
               // setDescribe(data);
                break;
        }
    }

    private void setLabel(Intent data) {
        label = data.getStringExtra("label");
        if (TextUtils.isEmpty(label)) {
            mLlLabel.removeAllViews();
            label = "";
            return;
        }
        // String[] labels = label.split("-");
        type = data.getIntExtra("positions", 200);
        mLlLabel.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 0);
        View view = LayoutInflater.from(this).inflate(R.layout.item_label_normal, mLlLabel, false);
        view.setLayoutParams(params);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
        tvLabel.setText(label);
        mLlLabel.addView(view);
        /*for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_label_normal, mLlLabel, false);
            view.setLayoutParams(params);
            TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
            tvLabel.setText(labels[i]);
            mLlLabel.addView(view);
        }*/
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
    }

    private void setListener() {
        mSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                mRlAddPic.setVisibility(View.VISIBLE);
            }else {
                mRlAddPic.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @param context      跳转起始页面
     * @param baseActivity 跳转目的页面
     */
    public static void start(Context context, Class<? extends BaseActivity> baseActivity) {
        Intent intent = new Intent(context, baseActivity);
        context.startActivity(intent);
    }


}
