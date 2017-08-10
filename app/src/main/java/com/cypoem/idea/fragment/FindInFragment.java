package com.cypoem.idea.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/4/21.
 */
public class FindInFragment extends BaseFragment {
    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    private HomeAdapter mAdapter;
    private int page = 1;
    private final int ROWS = 10;
    private int type;

    public static FindInFragment getFragment(int type) {
        FindInFragment fragment = new FindInFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPtr(true);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);

        toolbarSubtitle.setVisibility(View.GONE);
        toolbarTitle.setText("首页");
        setRecycler();
    }

    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeAdapter(getContext());
        mAdapter.fillList(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position) -> {
            HomePageBean homePageBean = mAdapter.getList().get(position);
            String writeId = String.valueOf(homePageBean.getWrite_id());
            String authorId = homePageBean.getUser().getUser_id();
            StartReadActivity.start(getContext(), writeId, authorId);
        });
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false, page)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        frame.postDelayed((() -> getData(true, page)), 100);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean isRefresh, int currentPage) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getDiscoverData(currentPage, ROWS,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<HomePageBean>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<List<HomePageBean>> response) {
                        if (isRefresh) {
                            mAdapter.getList().clear();
                        }
                        page++;
                        updateList(response.getResult());
                    }
                });
    }

    private void updateList(List<HomePageBean> result) {
        List<HomePageBean> list = mAdapter.getList();
        if (result.size() < ROWS) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        list.addAll(result);
        mAdapter.notifyDataSetChanged();
    }

}
