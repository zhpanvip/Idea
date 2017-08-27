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

public class CollectActivity extends BaseActivity {
    @BindView(R.id.lv_collect)
    ListView mListView;
    @BindView(R.id.ll_default)
    LinearLayout mLlDefault;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
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
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)-> {
            OpusBean opusBean = mAdapter.getList().get(position);
            String writeId = String.valueOf(opusBean.getWrite_id());
            String authorId = opusBean.getUser_id();
            String write_name = opusBean.getWrite_name();
            StartReadActivity.start(CollectActivity.this,writeId,authorId,write_name);
        });
    }

    private void initData() {
        setToolBarTitle("收藏");
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
    protected void setPtrHandler(View view) {
        super.setPtrHandler(mListView);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean showLoading,int page) {
        IdeaApi.getApiService()
                .getCollect(UserInfoTools.getUser(this).getUserId(),page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        if(response.getResult().size()< Constants.NUM){
                            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                        }else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                        }
                        mAdapter.getList().addAll(response.getResult());
                        mAdapter.notifyDataSetChanged();
                        if(response.getResult().size()==0){
                            mLlDefault.setVisibility(View.VISIBLE);
                            mTvNoData.setText("没有收藏的章节");
                        }
                    }
                });
    }
}
