package com.airong.core;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.view.View;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhpan on 2017/4/22.
 */

public interface BaseImpl {
    /**
     * 显示ProgressDialog
     */
    void showProgress(BaseImpl baseImpl);

    /**
     * 显示ProgressDialog
     *
     */
    void showProgress(BaseImpl baseImpl,String msg);

    /**
     * 取消ProgressDialog
     */
    void dismissProgress();

    /**
     *
     * @param content   内容
     * @param confirm   确定键文字
     * @param cancel    取消键文字
     * @param confirmListener   确定键监听
     * @param cancelListener    取消键监听
     */
    void showTwoButtonDialog(String content, String confirm, String cancel,
                             View.OnClickListener confirmListener,
                             View.OnClickListener cancelListener);
    /**
     * @param content   内容
     * @param confirm   确定键文字
     * @param cancel    取消键文字
     * @param confirmColor  确定键颜色
     * @param cancelColor   取消键颜色
     * @param confirmListener   确定键监听
     * @param cancelListener    取消键监听
     */
    void showTwoButtonDialog(String content, String confirm, String cancel,
                             @ColorInt int confirmColor, @ColorInt int cancelColor,
                             View.OnClickListener confirmListener,
                             View.OnClickListener cancelListener);
    /**
     *
     * @param content   内容
     * @param confirm   按钮文字
     * @param confirmListener   按钮监听
     */
    void showOneButtonDialog(String content,String confirm,View.OnClickListener confirmListener);


    boolean addRxStop(BaseImpl baseImpl, Disposable disposable);

    boolean addRxDestroy(BaseImpl baseImpl, Disposable disposable);

    void remove(BaseImpl baseImpl, Disposable disposable);


    /*View createDialog(Integer dialogLayoutRes, boolean cancelTouchOutside);
    void showDialog();
    void dismissDialog();*/
}
