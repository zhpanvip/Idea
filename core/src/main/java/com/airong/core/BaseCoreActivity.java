package com.airong.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.airong.core.utils.ToastUtils;
import com.airong.core.view.CustomProgressDialog;
import butterknife.ButterKnife;

/**
 * Created by zhpan on 2017/1/10.
 */

public abstract class BaseCoreActivity extends AppCompatActivity implements BaseImpl{
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mProgressDialog = CustomProgressDialog.createDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void showToast(String msg){
        ToastUtils.show(msg);
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    /**
     * 显示ProgressDialog
     */
    public void showProgress() {
        mProgressDialog.show();
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
