package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.SubjectAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.SubjectBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubjectActivity extends BaseActivity {

    @BindView(R.id.lv_subject)
    RecyclerView lvSubject;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_default)
    LinearLayout llDefault;

    private SubjectAdapter mAdapter;
    private int subjectId;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        initPtr(false);

        Intent intent = getIntent();
        subjectId = intent.getIntExtra("subjectId", 0);

        mAdapter = new SubjectAdapter(this);
        getSubject(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvSubject.setLayoutManager(linearLayoutManager);
        lvSubject.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<SubjectBean.WritesBean> list = mAdapter.getList();
                SubjectBean.WritesBean writesBean = list.get(position);
                StartReadActivity.start(SubjectActivity.this, writesBean.getWrite_id(), writesBean.getUser_id(), writesBean.getWrite_name());
            }
        });
    }

    @Override
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        super.onPtrLoadMoreBegin(frame);
        getSubject(false);
    }

    @Override
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {
        super.onPtrRefreshBegin(frame);
        getSubject(true);
    }

    private void getSubject(boolean isRefresh) {

        IdeaApi.getApiService()
                .getSubject(subjectId, page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<SubjectBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<SubjectBean> response) {
                        getDataSuccess(response, isRefresh);
                    }
                });
    }

    @Override
    protected void setPtrHandler(View view) {
        super.setPtrHandler(lvSubject);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getDataSuccess(BasicResponse<SubjectBean> response, boolean isRefresh) {
        SubjectBean result = response.getResult();
        List<SubjectBean.WritesBean> writes = result.getWrites();
        if (writes.size() < Constants.NUM) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        page++;
        mAdapter.getList().addAll(writes);
        mAdapter.notifyDataSetChanged();

        if (writes.size() == 0) {
            llDefault.setVisibility(View.VISIBLE);
            tvNoData.setText("暂无数据");
        }
    }

    public static void start(Context context, int subjectId) {
        Intent intent = new Intent(context, SubjectActivity.class);
        intent.putExtra("subjectId", subjectId);
        context.startActivity(intent);
    }

}
