package com.airong.core.dialog;

import android.content.Context;

import com.airong.core.R;


/**
 * Created by zhpan on 2017/5/26.
 * Description:
 */

public class DialogUtils {
    //  加载动画Dialog
    private CustomProgressDialog mProgressDialog;

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context activity, String msg) {
        if (activity == null) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.ProgressDialogStyle)
                    .setMessage(msg)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context activity) {
        if (activity == null) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.ProgressDialogStyle)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
