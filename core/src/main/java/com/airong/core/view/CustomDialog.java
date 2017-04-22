package com.airong.core.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.airong.core.R;
import com.airong.core.utils.DensityUtils;

/**
 * Created by zhpan on 2017/4/12.
 */

public class CustomDialog extends Dialog {
    //   dialog高度
    private int height;
    //  dialog宽度
    private int width;
    //  点击外部是否可以取消
    private boolean cancelTouchOutside;
    //  弹窗布局View
    private View dialogView;

    private CustomDialog(Builder builder) {
        super(builder.context);
        height = builder.height;
        width = builder.width;
        cancelTouchOutside = builder.cancelTouchOutside;
        dialogView = builder.mDialogView;
    }


    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        height = builder.height;
        width = builder.width;
        cancelTouchOutside = builder.cancelTouchOutside;
        dialogView = builder.mDialogView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(dialogView);

        setCanceledOnTouchOutside(cancelTouchOutside);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = height;
        lp.width = width;
        win.setAttributes(lp);
    }

    public static final class Builder {

        private Context context;
        private int height, width;
        private boolean cancelTouchOutside;
        private View mDialogView;
        private int resStyle = -1;

        public Builder(Context context) {
            this.context = context;

            mDialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
            //  计算dialog宽高
            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mDialogView.measure(measureSpec, measureSpec);
            height = mDialogView.getMeasuredHeight();
            width = mDialogView.getMeasuredWidth();
        }

        /**
         * @param dialogView 关联dialog布局文件的View
         * @return
         */
        public Builder setDialogLayout(View dialogView) {
            this.mDialogView = dialogView;
            return this;
        }

        public Builder setHeightPx(int val) {
            height = val;
            return this;
        }

        public Builder setWidthPx(int val) {
            width = val;
            return this;
        }

        public Builder setHeightDp(int val) {
            height = DensityUtils.dp2px(context, val);
            return this;
        }

        public Builder setWidthDp(int val) {
            width = DensityUtils.dp2px(context, val);
            return this;
        }

        /**
         * 设置主题
         *
         * @param resStyle
         * @return
         */
        public Builder setTheme(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        /**
         * 设置点击dialog外部是否取消dialog
         *
         * @param val
         * @return
         */
        public Builder cancelTouchOutside(boolean val) {
            cancelTouchOutside = val;
            return this;
        }

        /**
         * 给dialog中的view添加点击事件
         *
         * @param viewResId 被点击view的id
         * @param listener
         * @return
         */
        public Builder addViewOnclick(int viewResId, View.OnClickListener listener) {
            mDialogView.findViewById(viewResId).setOnClickListener(listener);
            return this;
        }

        /**
         * 确定键监听
         * @param confirm
         * @param listener
         * @return
         */
        public Builder addConfirmClickListener(String confirm, View.OnClickListener listener) {
            TextView tvConfirm = (TextView) mDialogView.findViewById(R.id.tv_confirm);
            tvConfirm.setText(confirm);
            tvConfirm.setOnClickListener(listener);
            return this;
        }

        /**
         * 取消键监听
         * @param cancel
         * @param listener
         * @return
         */
        public Builder addCancelClickListener(String cancel, View.OnClickListener listener) {
            TextView tvCancel = (TextView) mDialogView.findViewById(R.id.tv_cancel);
            tvCancel.setText(cancel);
            tvCancel.setOnClickListener(listener);
            return this;
        }

        /**
         * 设置内容
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            TextView tvTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_content);
            tvTitle.setText(content);
            return this;
        }

        /**
         * 设置取消键颜色
         * @param color 颜色
         * @return
         */
        public Builder setCancelColor(String color){
            TextView tvCancel= (TextView) mDialogView.findViewById(R.id.tv_cancel);
            tvCancel.setTextColor(Color.parseColor(color));
            return this;
        }
        /**
         * 设置确定键颜色
         * @param color 颜色
         * @return
         */
        public Builder setConfirmColor(String color){
            TextView tvCancel= (TextView) mDialogView.findViewById(R.id.tv_confirm);
            tvCancel.setTextColor(Color.parseColor(color));
            return this;
        }

        /**
         * 显示一个按钮的弹窗
         * @return
         */
        public Builder showOneButton() {
                mDialogView.findViewById(R.id.tv_cancel).setVisibility(View.GONE);
                mDialogView.findViewById(R.id.view_dialog).setVisibility(View.GONE);
            return this;
        }

        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this, resStyle);
            } else {
                return new CustomDialog(this);
            }
        }
    }
}
