package com.airong.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.airong.core.utils.ToastUtils;
import com.airong.core.view.CustomProgressDialog;
import com.airong.core.view.CustomDialog;

public abstract class BaseCoreActivity extends AppCompatActivity {
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;
    //  对话框
    private CustomDialog dialog;
    //  对话框布局的View
    private View dialogView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = CustomProgressDialog.createDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }


    public void showToast(String msg) {
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
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 创建对话框
     * @param dialogLayoutRes dialog布局资源文件
     * @return
     */
    public View createDialog(Integer dialogLayoutRes) {
        if (dialogLayoutRes == null) {
            dialogLayoutRes = R.layout.custom_dialog;
        }
        dialogView = LayoutInflater.from(this).inflate(dialogLayoutRes, null);
        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.SrcbDialog)
                .setHeightDimenRes(R.dimen.dilog_height)
                .setWidthDimenRes(R.dimen.dilog_width)
                .cancelTouchOutside(false)
                .setDialogLayout(dialogView).build();
        return dialogView;
    }

    /**
     * create dialog
     * @param dialogLayoutRes    dialog布局资源文件
     * @param cancelTouchOutside 点击外部是否可以取消
     * @return
     */
    public View createDialog(Integer dialogLayoutRes, boolean cancelTouchOutside) {
        if (dialogLayoutRes == null) {
            dialogLayoutRes = R.layout.custom_dialog;
        }
        dialogView = LayoutInflater.from(this).inflate(dialogLayoutRes, null);
        dialog = new CustomDialog.Builder(this)
                .setTheme(R.style.SrcbDialog)
                .setHeightDimenRes(R.dimen.dilog_height)
                .setWidthDimenRes(R.dimen.dilog_width)
                .cancelTouchOutside(cancelTouchOutside)
                .setDialogLayout(dialogView).build();
        return dialogView;
    }

    /**
     * 显示dialog
     */
    public void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
