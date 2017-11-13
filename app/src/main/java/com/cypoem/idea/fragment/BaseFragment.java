package com.cypoem.idea.fragment;

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
 *
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
                return super.checkCanDoLoadMore(frame, null == view ? content : view, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, null == view ? content : view, header);
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
