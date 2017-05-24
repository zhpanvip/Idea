package com.airong.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.airong.core.utils.ToastUtils;
import com.airong.core.dialog.CustomProgressDialog;

/**
 * Created by zhpan on 2017/1/10.
 */

public abstract class BaseCoreActivity extends AppCompatActivity implements BaseImpl{
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String msg){
        ToastUtils.show(msg);
    }

    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress(String msg) {
        mProgressDialog= new CustomProgressDialog.Builder(this)
                .setMessage(msg)
                .setTheme(R.style.ProgressDialogStyle)
                .build();
        mProgressDialog.show();
    }
    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress() {
        mProgressDialog= new CustomProgressDialog.Builder(this)
                .setTheme(R.style.ProgressDialogStyle)
                .build();
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
