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
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.UserList;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<UserBean> list = adapter.getList();
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

        List<UserBean> mList = new ArrayList<>();
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
                    .getMyFocus(userId, currentPage, Constants.NUM, type)
                    .subscribeOn(Schedulers.io())
                    .compose(bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<UserList>>(this, false) {
                        @Override
                        public void onSuccess(BasicResponse<UserList> response) {
                            getDataSuccess(response, isRefresh);
                        }
                    });
        } else if (type == Constants.HOT_AUTHOR) {
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            params.put("rows", Constants.NUM);
            params.put("type", 1);
            IdeaApi.getApiService()
                    .getHotUsers(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<UserList>>(this, true) {
                        @Override
                        public void onSuccess(BasicResponse<UserList> response) {
                            getDataSuccess(response, isRefresh);
                        }
                    });
        }
    }

    private void getDataSuccess(BasicResponse<UserList> response, boolean isRefresh) {
        List<UserBean> users = response.getResult().getUsers();
        if (users.size() < Constants.NUM) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        page++;
        mAdapter.getList().addAll(users);
        mAdapter.notifyDataSetChanged();
        if (users.size() == 0 && isRefresh) {
            mLlDefault.setVisibility(View.VISIBLE);
            if (type == Constants.FOLLOWS)
                mTvNoData.setText("没有粉丝");
            else if (type == Constants.FOCUS)
                mTvNoData.setText("没有关注任何用户");
        }
    }
}
