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
    @BindView(R.id.ll_default)
    LinearLayout mLlDefault;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    private FansAdapter mAdapter;
    private int page = 1;
    private int type;
    private String userId;

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
            AuthorInfoActivity.start(FansActivity.this, list.get(position).getUserId());

        });
    }

    private void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        userId = intent.getStringExtra("userId");

        if (type == Constants.FOCUS)
            setToolBarTitle("关注");
        else if (type == Constants.FOLLOWS)
            setToolBarTitle("粉丝");

        List<FansBean> mList = new ArrayList<>();
        mAdapter = new FansAdapter(this, R.layout.item_fans);
        mAdapter.setList(mList);
        lvFans.setAdapter(mAdapter);
        getData(true, page);
    }


    public static void start(Context context, int type, String userId) {
        Intent intent = new Intent(context, FansActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false, page)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        mAdapter.getList().clear();
        frame.postDelayed((() -> getData(true, page)), 100);
    }

    @Override
    protected void setPtrHandler(View view) {
        super.setPtrHandler(lvFans);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean isRefresh, int currentPage) {
        if (type == Constants.FOCUS) {
            IdeaApi.getApiService()
                    .getMyFocus(userId, currentPage, Constants.NUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<FansBean>>>(this, false) {
                        @Override
                        public void onSuccess(BasicResponse<List<FansBean>> response) {
                            getDataSuccess(response, isRefresh);
                        }
                    });
        } else if (type == Constants.FOLLOWS) {
            IdeaApi.getApiService()
                    .getMyFollows(userId, currentPage, Constants.NUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<List<FansBean>>>(this, true) {
                        @Override
                        public void onSuccess(BasicResponse<List<FansBean>> response) {
                            getDataSuccess(response, isRefresh);
                        }
                    });
        }
    }

    private void getDataSuccess(BasicResponse<List<FansBean>> response, boolean isRefresh) {
        if (response.getResult().size() < Constants.NUM) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        page++;
        mAdapter.getList().addAll(response.getResult());
        mAdapter.notifyDataSetChanged();
        if (response.getResult().size() == 0 && isRefresh) {
            mLlDefault.setVisibility(View.VISIBLE);
            if (type == Constants.FOLLOWS)
                mTvNoData.setText("没有粉丝");
            else if (type == Constants.FOCUS)
                mTvNoData.setText("没有关注任何用户");
        }
    }
}
