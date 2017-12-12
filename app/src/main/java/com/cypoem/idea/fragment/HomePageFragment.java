package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.CreateCircleActivity;
import com.cypoem.idea.adapter.CircleAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CircleBean;
import com.cypoem.idea.module.bean.CircleListBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class HomePageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rv_home)
    RecyclerView recyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_default)
    LinearLayout mLlDefault;
    private CircleAdapter adapter;
    private int page = 1;

    public static HomePageFragment getFragment() {
        return new HomePageFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        setRefreshLayout(true);
    }


    @Override
    public void onRefresh() {
        getData();
    }

    //  请求数据
    private void getData() {
        IdeaApi.getApiService()
                .getMyCircle(UserInfoTools.getUserId(getContext()), page, Constants.NUM, 1)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<CircleListBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<CircleListBean> response) {
                        CircleListBean circleListBean = response.getResult();
                        List<CircleBean> circles = circleListBean.getCircles();
                        setRecyclerView(circles);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setRecyclerView(List<CircleBean> list) {
        if (list.size() <= 1) {
            mLlDefault.setVisibility(View.VISIBLE);
        } else {
            mLlDefault.setVisibility(View.GONE);
        }
        adapter = new CircleAdapter(getContext());
        adapter.fillList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("创建圈子");
        toolbarTitle.setText("关注");
        toolbarSubtitle.setOnClickListener(v -> CreateCircleActivity.start(getContext()));
    }

}
