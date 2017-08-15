package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.view.ScrollableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/5/23.
 */

public class AuthorFragment extends BaseFragment implements ScrollableHelper.ScrollableContainer {
    private static final int ROWS = 10;
    @BindView(R.id.lv_author)
    ListView mListView;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    private CollectAdapter mAdapter;

    private int page = 1;
    private String userId;
    private int type;


    public static AuthorFragment getFragment(int type, String userId) {
        AuthorFragment authorFragment = new AuthorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("userId", userId);
        authorFragment.setArguments(bundle);
        return authorFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_author;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        setListener();
        initPtr(false);
        mPtrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
    }

    @Override
    protected void initPtr(boolean isAutoRefresh) {
        super.initPtr(isAutoRefresh);
    }

    private void initData() {
        Bundle arguments = getArguments();
        userId = arguments.getString("userId");
        type = arguments.getInt("type", 1);
        List<OpusBean> mList = new ArrayList<>();
        mAdapter = new CollectAdapter(getContext(), R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
        getData(page);
    }

    private void getData(int currentPage) {
        IdeaApi.getApiService()
                .getMyOpus(userId, currentPage, ROWS, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        List<OpusBean> result = response.getResult();
                        if (result.size() < Constants.NUM) {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
                        } else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
                        }
                        if(result.size()==0){
                            mListView.setVisibility(View.GONE);
                            mTvNoData.setText("暂无数据");
                        }
                        mAdapter.getList().addAll(response.getResult());
                        mAdapter.notifyDataSetChanged();
                        page++;
                    }
                });
    }


    @Override
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        super.onPtrLoadMoreBegin(frame);
        getData(page);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    @Override
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {
        super.onPtrRefreshBegin(frame);
        page = 1;
        mAdapter.getList().clear();
        getData(page);
    }

    private void setListener() {
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                    OpusBean opusBean = mAdapter.getList().get(position);
                    String uid = opusBean.getUid();
                    StartReadActivity.start(getContext(), "", "");
                }
        );
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }
}
