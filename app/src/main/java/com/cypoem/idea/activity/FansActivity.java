package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.FansAdapter;
import com.cypoem.idea.module.bean.FansBean;
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

public class FansActivity extends BaseActivity {

    @BindView(R.id.lv_fans)
    ListView lvFans;
    private FansAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPtr(false);
        setListener();
    }

    private void setListener() {
        lvFans.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)->
                AuthorInfoActivity.start(FansActivity.this));
    }

    private void initData() {
        List<FansBean> mList=new ArrayList<>();
        FansBean fansBean=new FansBean();
        fansBean.setCreate(15);
        fansBean.setHeadUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=304866327,2141533711&fm=11&gp=0.jpg");
        fansBean.setJoin(55);
        fansBean.setName("韩三少");
        fansBean.setSing("一直被模仿，从未被超越");
        fansBean.setPublish(10);
        mList.add(fansBean);
        mList.add(fansBean);
        mList.add(fansBean);
        mList.add(fansBean);
        mAdapter=new FansAdapter(this,R.layout.item_fans);
        mAdapter.setList(mList);
        lvFans.setAdapter(mAdapter);
    }

    public static void start(Context context){
        Intent intent=new Intent(context,FansActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false)), 100);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean showLoading) {
      //  Toast.makeText(this, "请求数据成功", Toast.LENGTH_SHORT).show();

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
