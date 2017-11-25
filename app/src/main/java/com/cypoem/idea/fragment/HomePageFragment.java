package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CircleAdapter;
import com.cypoem.idea.module.bean.CircleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        setRecyclerView();
        setRefreshLayout();
    }

    private void setRefreshLayout() {
        // refreshLayout.setProgressViewEndTarget(false, 300);
        /*refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        refreshLayout.post(() -> {
            refreshLayout.setRefreshing(true);
            onRefresh();
        });
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            refreshLayout.setRefreshing(false);
            List<CircleBean> list = adapter.getList();
            list.add(new CircleBean());
            adapter.notifyDataSetChanged();
            if (list.size() <= 1) {
                mLlDefault.setVisibility(View.VISIBLE);
            } else {
                mLlDefault.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void setRecyclerView() {
        adapter = new CircleAdapter(getContext());
        List<CircleBean> list = new ArrayList<>();

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
        // mViewStub.inflate();
    }

}
