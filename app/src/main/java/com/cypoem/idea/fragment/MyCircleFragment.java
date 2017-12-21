package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CircleListAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CircleBean;
import com.cypoem.idea.module.bean.CircleResponse;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/12/20.
 */

public class MyCircleFragment extends BaseFragment {

    public static final int FOCUS_CIRCLE = 2;
    public static final int MY_CIRCLE = 1;

    @BindView(R.id.rv_circle)
    RecyclerView mRecyclerView;
    private int page = 1;
    private int type;

    public static MyCircleFragment getFragment(int type) {
        MyCircleFragment fragment = new MyCircleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        setRefreshLayout(true);
    }

    private void setCircleList(List<CircleBean> hostWrites) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        CircleListAdapter adapter = new CircleListAdapter(getContext());
        adapter.fillList(hostWrites);
        mRecyclerView.setAdapter(adapter);
    }

    //  请求数据
    private void getData() {
        IdeaApi.getApiService()
                .getMyCircle(UserInfoTools.getUserId(getContext()),page, Constants.NUM, type)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<CircleResponse>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<CircleResponse> response) {
                        CircleResponse result = response.getResult();
                        List<CircleBean> circles = result.getCircles();
                        setCircleList(circles);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getData();
    }
}
