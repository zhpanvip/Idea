package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.module.bean.CollectBean;
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

public class CollectActivity extends BaseActivity {

    @BindView(R.id.lv_collect)
    ListView mListView;
    private CollectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPtr(false);
        setListener();
    }

    private void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StartReadActivity.start(CollectActivity.this);
            }
        });
    }

    private void initData() {
        List<CollectBean> mList=new ArrayList<>();
        CollectBean collectBean=new CollectBean();
        collectBean.setTime("2017-05-20");
        collectBean.setPicUrl("http://pic17.nipic.com/20111020/6337790_120550160000_2.jpg");
        collectBean.setArticlesNum(15);
        collectBean.setAuther("含箫剑");
        collectBean.setReadNum(565);
        collectBean.setPraiseNum(122);
        collectBean.setTitle("一只单身汪的日常");
        collectBean.setSubTitle("汪汪汪");
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mAdapter=new CollectAdapter(this,R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
    }
    public static void start(Context context){
        Intent intent=new Intent(context,CollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(true)), 100);
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
                        showToast( "请求数据成功");
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size() - content.size() + 2; i++) {
                            showToast("Url:" + content.get(i).getUrl());
                        }
                    }
                });
    }
}
