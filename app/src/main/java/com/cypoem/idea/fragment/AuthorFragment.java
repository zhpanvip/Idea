package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.CollectActivity;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CollectBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.ListViewForScrollView;
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
    private CollectAdapter mAdapter;

    private int page=1;
    private String userId;


    public static AuthorFragment getFragment(Bundle bundle) {
        AuthorFragment authorFragment = new AuthorFragment();
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
    }

    private void initData() {
        Bundle arguments = getArguments();
        userId = arguments.getString("userId");
        List<OpusBean> mList = new ArrayList<>();
        mAdapter = new CollectAdapter(getContext(), R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);

        getData(true,page);
    }

    private void getData(boolean showLoading, int page) {
        IdeaApi.getApiService()
                .getMyJoinOpus(userId,page,ROWS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        mAdapter.getList().addAll(response.getResult());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        super.onPtrLoadMoreBegin(frame);
        getData(false,++page);
    }

    @Override
    protected void onPtrRefreshBegin(PtrFrameLayout frame) {
        super.onPtrRefreshBegin(frame);
        page=1;
        mAdapter.getList().clear();
        getData(false,page);
    }

    private void setListener() {
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->
                StartReadActivity.start(getContext(),mAdapter.getList().get(position).getUid())
        );
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }
}
