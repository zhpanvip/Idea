package com.airong.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airong.core.view.CustomProgressDialog;

import butterknife.ButterKnife;

public abstract class BaseCoreFragment extends Fragment implements BaseImpl{
    public View rootView;
    public LayoutInflater inflater;

    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.inflater=inflater;
        mProgressDialog = CustomProgressDialog.createDialog(getContext());
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            init();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    /**
     * 显示ProgressDialog
     */
    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    /**
     * 取消ProgressDialog
     */
    @Override
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    protected abstract int getLayoutId();

    protected abstract void init();
}
