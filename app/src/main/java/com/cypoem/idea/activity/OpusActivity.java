package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OpusActivity extends BaseActivity {
    @BindView(R.id.lv_opus)
    ListView mListView;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.ll_default)
    LinearLayout mLlDefault;
    private CollectAdapter mAdapter;
    private int page=1;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opus;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initPtr(false);
        initData();
        setListener();
    }

    private void setListener() {
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)-> {
            OpusBean opusBean = mAdapter.getList().get(position);
            String writeId = String.valueOf(opusBean.getWrite_id());
            String authorId = opusBean.getUser_id();
            String write_name = opusBean.getWrite_name();
            StartReadActivity.start(OpusActivity.this,writeId,authorId,write_name);
        });
    }

    private void initData() {
        Intent intent = getIntent();
        type=intent.getIntExtra("type",1);
        setRecyclerView();
    }

    private void setRecyclerView() {
        List<OpusBean> mList = new ArrayList<>();
        mAdapter = new CollectAdapter(this, R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
        getData(false,page);
    }

    public static void start(Context context,int type) {
        Intent intent = new Intent(context, OpusActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        super.onPtrLoadMoreBegin(frame);
        getData(false,page);
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
        getData(true,page);
    }

    @Override
    protected void setPtrHandler(View view) {
        super.setPtrHandler(mListView);
    }

    private void getData(boolean isRefresh, int currentPage) {
        IdeaApi.getApiService()
                .getMyOpus(UserInfoTools.getUser(this).getUserId(),currentPage, Constants.NUM,type)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this,true) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        List<OpusBean> result = response.getResult();
                        if(result.size()<Constants.NUM){
                            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                        }else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                        }
                        if(isRefresh){
                            mAdapter.getList().clear();
                        }
                        mAdapter.getList().addAll(result);
                        mAdapter.notifyDataSetChanged();
                        page++;
                        setDefaultPage(result.size());
                    }
                });
    }

    private void setDefaultPage(int size) {
        if(size==0){
            mLlDefault.setVisibility(View.VISIBLE);
            switch (type){
                case Constants.MY_START_OPUS:
                    mTvNoData.setText("没有发起的作品");
                    break;
                case Constants.MY_OWN_OPUS:
                    mTvNoData.setText("没有自己的作品");
                    break;
                case Constants.MY_JOIN_OPUS:
                    mTvNoData.setText("没有参与任何作品");
                    break;
                case Constants.MY_DRAFT:
                    mTvNoData.setText("还没有草稿呢");
                    break;
            }
        }
    }
}
