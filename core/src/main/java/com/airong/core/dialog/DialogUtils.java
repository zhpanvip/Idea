package com.airong.core.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.airong.core.R;


/**
 * Created by zhpan on 2017/5/26.
         * Description:Dialog工具类
         */

public class DialogUtils {
    //  加载动画Dialog
    private CustomProgressDialog mProgressDialog;
    private CustomDialog dialog;
    private Activity activity;


    public DialogUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(String msg) {
        if (activity == null && activity.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.custom_dialog2)
                    .setMessage(msg)
                    .setCancelable(true)
                    .build();
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress() {
        if (activity == null && activity.isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog.Builder(activity)
                    .setTheme(R.style.custom_dialog2)
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

    /**
     * @param content         内容
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    public void showTwoButtonDialog(String content,
                                    View.OnClickListener confirmListener,
                                    View.OnClickListener cancelListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        dialog = new CustomDialog.Builder(activity)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .setCancelable(true)
                .addConfirmClickListener("确定", confirmListener)
                .addCancelClickListener("取消", cancelListener)
                .build();
        dialog.show();
    }

    /**
     * @param content         内容
     * @param confirm         确定键文字
     * @param cancel          取消键文字
     * @param confirmColor    确定键颜色
     * @param cancelColor     取消键颜色
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    public void showTwoButtonDialog(String content, String confirm, String cancel,
                                    @ColorInt int confirmColor, @ColorInt int cancelColor,
                                    View.OnClickListener confirmListener,
                                    View.OnClickListener cancelListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        dialog = new CustomDialog.Builder(activity)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .setConfirmColor(confirmColor)
                .setCancelColor(cancelColor)
                .addConfirmClickListener(confirm, confirmListener)
                .addCancelClickListener(cancel, cancelListener)
                .build();
        dialog.show();
    }

    /**
     * @param content         内容
     * @param confirm         按钮文字
     * @param confirmListener 按钮监听
     */
    public void showOneButtonDialog(String content, String confirm, View.OnClickListener confirmListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        dialog = new CustomDialog.Builder(activity)
                .setTheme(R.style.IdeaDialog)
                .setContent(content)
                .addConfirmClickListener(confirm, confirmListener)
                .showOneButton()
                .setCancelable(false)
                .build();
        dialog.show();
    }


    /**
     * create custom dialog
     *
     * @param dialogLayoutRes    dialog布局资源文件
     * @param cancelTouchOutside 点击外部是否可以取消
     * @return 自定义的dialog对应的View
     */
    public View createDialog(@LayoutRes Integer dialogLayoutRes, boolean cancelTouchOutside) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        if (dialogLayoutRes == null) {
            dialogLayoutRes = R.layout.custom_dialog;
        }
        View dialogView = LayoutInflater.from(activity).inflate(dialogLayoutRes, null);
        //  计算dialog宽高
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogView.measure(measureSpec, measureSpec);
        int height = dialogView.getMeasuredHeight();
        int width = dialogView.getMeasuredWidth();

        dialog = new CustomDialog.Builder(activity)
                .setTheme(R.style.IdeaDialog)
                .setHeightPx(height)
                .setWidthPx(width)
                .setCancelable(true)
                .cancelTouchOutside(cancelTouchOutside)
                .setDialogLayout(dialogView).build();
        dialog.show();
        return dialogView;
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
