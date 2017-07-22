package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.FansAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.FansBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

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
    private int page = 1;
    private int type;

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
        lvFans.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            FansAdapter adapter = (FansAdapter) parent.getAdapter();
            List<FansBean> list = adapter.getList();
            AuthorInfoActivity.start(FansActivity.this, list.get(position).getUid());

        });
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        List<FansBean> mList = new ArrayList<>();
        mAdapter = new FansAdapter(this, R.layout.item_fans);
        mAdapter.setList(mList);
        lvFans.setAdapter(mAdapter);
        getData(true, page);
    }


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, FansActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false, ++page)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        mAdapter.getList().clear();
        frame.postDelayed((() -> getData(false, page)), 100);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean showLoading, int page) {
        if (type == Constants.FOCUS) {
            IdeaApi.getApiService()
                    .getMyFocus(UserInfoTools.getUserId(this), page, Constants.NUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<FansBean>>>(this, showLoading) {
                        @Override
                        public void onSuccess(BasicResponse<List<FansBean>> response) {
                            if (response.getResult().size() < Constants.NUM) {
                                mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                            } else {
                                mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                            }
                            mAdapter.getList().addAll(response.getResult());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        }else if(type==Constants.FOLLOWS){
            IdeaApi.getApiService()
                    .getMyFollows(UserInfoTools.getUserId(this), page, Constants.NUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<FansBean>>>(this, showLoading) {
                        @Override
                        public void onSuccess(BasicResponse<List<FansBean>> response) {
                            if (response.getResult().size() < Constants.NUM) {
                                mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                            } else {
                                mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                            }
                            mAdapter.getList().addAll(response.getResult());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
}
