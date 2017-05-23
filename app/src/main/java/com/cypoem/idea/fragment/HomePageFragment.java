package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.module.bean.Article;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
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
 *
 */
public class HomePageFragment extends BaseFragment {
    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private HomeAdapter mAdapter;

    public static HomePageFragment getInstance(int type) {
        HomePageFragment fragment = new HomePageFragment();
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
    protected void init() {
        initData();
        initPtr(false);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int type = bundle.getInt("type");
            if (type == FindFragment.HOTEST || type == FindFragment.NEWEST) {
                rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);
            }
        }
        toolbarSubtitle.setVisibility(View.GONE);
        toolbarTitle.setText("首页");

        List<Article> mList = new ArrayList<>();
        Article article1 = new Article();
        article1.setPicUrl("http://img5.imgtn.bdimg.com/it/u=2740166231,410053698&fm=23&gp=0.jpg");
        Article article2 = new Article();
        article2.setPicUrl("http://img0.imgtn.bdimg.com/it/u=4194417426,3508932&fm=23&gp=0.jpg");
        Article article3 = new Article();
        article3.setPicUrl("http://img1.imgtn.bdimg.com/it/u=3520066001,2415029280&fm=23&gp=0.jpg");
        Article article4 = new Article();
        article4.setPicUrl("http://img3.imgtn.bdimg.com/it/u=3288109620,1606550125&fm=23&gp=0.jpg");
        Article article5 = new Article();
        article5.setPicUrl("http://img1.imgtn.bdimg.com/it/u=3341488424,2638510557&fm=23&gp=0.jpg");
        mList.add(article1);
        mList.add(article2);
        mList.add(article3);
        mList.add(article4);
        mList.add(article5);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeAdapter(getContext());
        mAdapter.fillList(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false)), 1000);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false)), 1000);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean showLoading) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this, showLoading) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(getContext(), "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size() - content.size() + 2; i++) {
                            Toast.makeText(getContext(), "Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
