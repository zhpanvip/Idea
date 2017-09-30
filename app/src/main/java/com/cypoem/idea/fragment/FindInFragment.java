package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.BaseOpusBean;
import com.cypoem.idea.module.bean.HomeBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
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
    @BindView(R.id.rv_write)
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
        return R.layout.fragment_find_in;
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
            HomeBean.WritesBean homePageBean = (HomeBean.WritesBean) mAdapter.getList().get(position);
            String writeId = String.valueOf(homePageBean.getWrite_id());
            String authorId = homePageBean.getUser().getUser_id();
            String write_name = homePageBean.getWrite_name();
            StartReadActivity.start(getContext(), writeId, authorId,write_name);
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

    public void dismissProgress() {
       // super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean isRefresh, int currentPage) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getDiscoverData(currentPage, ROWS,type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<HomeBean.WritesBean>>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BasicResponse<List<HomeBean.WritesBean>> response) {
                        mPtrFrame.refreshComplete();
                        if (isRefresh) {
                            mAdapter.getList().clear();
                        }
                        page++;
                        updateList(response.getResult());
                    }

                    @Override
                    public void onFail(BasicResponse<List<HomeBean.WritesBean>> response, int code) {
                        super.onFail(response, code);
                        mPtrFrame.refreshComplete();
                    }

                    @Override
                    public void onException(ExceptionReason reason) {
                        super.onException(reason);
                        mPtrFrame.refreshComplete();
                    }
                });
    }

    private void updateList(List<HomeBean.WritesBean> result) {
        List<BaseOpusBean> list = mAdapter.getList();
        if (result.size() < ROWS) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        list.addAll(result);
        mAdapter.notifyDataSetChanged();
    }

}
