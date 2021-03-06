package com.cypoem.idea.fragment;

import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;

import com.airong.core.BaseLazyFragment;
import com.airong.core.utils.ToastUtils;
import com.airong.core.dialog.CustomDialog;
import com.airong.core.view.PtrClassicListFooter;
import com.airong.core.view.PtrClassicListHeader;
import com.cypoem.idea.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhpan on 2017/4/22.
 */

public abstract class BaseFragment extends BaseLazyFragment {

    PtrClassicFrameLayout mPtrFrame;
    //  对话框
    private CustomDialog dialog;
    Unbinder unbinder;

    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getLocalClassName());
    }


    /**
     * @param content         内容
     * @param confirm         确定键文字
     * @param cancel          取消键文字
     * @param confirmListener 确定键监听
     * @param cancelListener  取消键监听
     */
    public void showTwoButtonDialog(String content, String confirm, String cancel,
                                    View.OnClickListener confirmListener,
                                    View.OnClickListener cancelListener) {
        dialog = new CustomDialog.Builder(getActivity())
                .setTheme(com.airong.core.R.style.IdeaDialog)
                .setContent(content)
                .addConfirmClickListener(confirm, confirmListener)
                .addCancelClickListener(cancel, cancelListener)
                .build();
        dialog.show();
    }

    //  初始化刷新加载框架，子类中需要的时候调用
    protected void initPtr(boolean isAutoRefresh) {
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.list_view_frame);
        if (mPtrFrame == null) return;

        mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        PtrClassicListHeader header = new PtrClassicListHeader(getActivity());
        header.setLastUpdateTimeRelateObject(getActivity());
        PtrClassicListFooter footer = new PtrClassicListFooter(getActivity());
        footer.setLastUpdateTimeRelateObject(getActivity());
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setFooterView(footer);
        mPtrFrame.addPtrUIHandler(footer);
        setPtrHandler(null);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        if (isAutoRefresh)
            mPtrFrame.postDelayed((() -> mPtrFrame.autoRefresh()), 200);
    }

    protected void setPtrHandler(View view) {
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                onPtrLoadMoreBegin(frame);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onPtrRefreshBegin(frame);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, null==view?content:view, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, null==view?content:view, header);
            }
        });
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
        dialog = new CustomDialog.Builder(getActivity())
                .setTheme(com.airong.core.R.style.IdeaDialog)
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
        dialog = new CustomDialog.Builder(getActivity())
                .setTheme(com.airong.core.R.style.IdeaDialog)
                .setContent(content)
                .addConfirmClickListener(confirm, confirmListener)
                .showOneButton()
                .build();
        dialog.show();
    }

    /**
     * 上拉加载
     */
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {

    }

    /**
     * 下拉刷新
     */
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {

    }

    /**
     * create custom dialog
     *
     * @param dialogLayoutRes    dialog布局资源文件
     * @param cancelTouchOutside 点击外部是否可以取消
     */
    protected View createDialog(Integer dialogLayoutRes, boolean cancelTouchOutside) {
        if (dialogLayoutRes == null) {
            dialogLayoutRes = com.airong.core.R.layout.custom_dialog;
        }
        View dialogView = LayoutInflater.from(getActivity()).inflate(dialogLayoutRes, null);
        //  计算dialog宽高
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogView.measure(measureSpec, measureSpec);
        int height = dialogView.getMeasuredHeight();
        int width = dialogView.getMeasuredWidth();

        dialog = new CustomDialog.Builder(getActivity())
                .setTheme(com.airong.core.R.style.IdeaDialog)
                .setHeightPx(height)
                .setWidthPx(width)
                .cancelTouchOutside(cancelTouchOutside)
                .setDialogLayout(dialogView).build();
        dialog.show();
        return dialogView;
    }

    /**
     * 隐藏dialog
     */
    protected void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
