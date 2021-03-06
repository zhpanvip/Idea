package com.airong.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.airong.core.dialog.CommonDialogUtils;
import com.airong.core.utils.SnackbarUtils;
import com.airong.core.utils.ToastUtils;
import com.airong.core.dialog.CustomProgressDialog;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by zhpan on 2017/1/10.
 */

public abstract class BaseCoreActivity extends RxAppCompatActivity {
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;
  //  protected CommonDialogUtils mDialogUtils;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mDialogUtils=new CommonDialogUtils();
    }

    public void showToast(String msg){
        ToastUtils.show(msg);
    }

    public void showSnackBar(View parent,String msg){
        SnackbarUtils.showShortSnackbar(parent,msg, Color.parseColor("#FFFFFF"),Color.parseColor("#222222"));

    }

    public void showToast(@StringRes  int resId){
        ToastUtils.show(resId);
    }


    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
       // mDialogUtils.dismissProgress();
        if (mProgressDialog != null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
