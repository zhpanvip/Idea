package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.OpusAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OpusActivity extends BaseActivity {
    @BindView(R.id.rv_opus)
    RecyclerViewPager mRecyclerView;
    @BindView(R.id.ll_tab)
    LinearLayout mLlTab;
    private OpusAdapter mAdapter;
    private int page=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opus;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initPtr(false);
        initData();
    }

    private void initData() {
        setRecyclerView();

        getData(true,page);
    }

    private void setRecyclerView() {
        List<ArticleBean> list=new ArrayList<>();
        mAdapter=new OpusAdapter(this,OpusAdapter.OPUS);
        mAdapter.setList(list);

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnPageChangedListener((int i, int i1)-> showToast("vertical position="+i1));

        mRecyclerView.addOnPageChangedListener((int i, int i1)-> startAnim());

    }

    public void startAnim(){
        Animation animation = AnimationUtils
                .loadAnimation(OpusActivity.this, R.anim.draw_down);
        mLlTab.startAnimation(animation);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OpusActivity.class);
        context.startActivity(intent);
    }

    private void getData(boolean showLoading, int page) {
        IdeaApi.getApiService()
                .getMyJoinOpus(UserInfoTools.getUser(this).getUid(),page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        if(response.getResult().size()<Constants.NUM){
                            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                        }else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                        }
                        //mAdapter.getList().addAll(response.getResult());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        super.onPtrLoadMoreBegin(frame);
        getData(false,++page);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    @Override
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {
        super.onPtrRefreshBegin(frame);
        page=1;
        mAdapter.getList().clear();
        getData(false,page);
    }
}
