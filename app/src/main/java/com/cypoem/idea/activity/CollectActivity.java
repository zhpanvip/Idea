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
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CollectBean;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectActivity extends BaseActivity {

    private static final int ROWS = 10;
    @BindView(R.id.lv_collect)
    ListView mListView;
    private CollectAdapter mAdapter;
    private int page=1;

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
                StartReadActivity.start(CollectActivity.this,mAdapter.getList().get(position).getUid());
            }
        });
    }

    private void initData() {
        List<OpusBean> mList = new ArrayList<>();
        mAdapter = new CollectAdapter(this, R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);

        getData(true,page);
    }



    public static void start(Context context){
        Intent intent=new Intent(context,CollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false,++page)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        page=1;
        mAdapter.getList().clear();
        frame.postDelayed((() -> getData(true,page)), 100);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean showLoading,int page) {
        IdeaApi.getApiService()
                .getMyJoinOpus(UserInfoTools.getUser(this).getUid(),page,ROWS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        if(response.getResult().size()<ROWS){
                            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                        }else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                        }
                        mAdapter.getList().addAll(response.getResult());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
