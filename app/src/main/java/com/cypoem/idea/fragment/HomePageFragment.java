package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.view.CircleViewPager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/4/21.
 */
public class HomePageFragment extends BaseFragment {
    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    private HomeAdapter mAdapter;
    private View headerView;
    private int lastX;
    private int lastY;
    private CircleViewPager circleViewPager;
    private int page=1;
    private int rows=10;


    public static HomePageFragment getInstance(int type) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPtr(false);
        getData(true);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int type = bundle.getInt("type");
            if (type == FindFragment.HOTEST || type == FindFragment.NEWEST) {
                rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);
            }
        }
        toolbarSubtitle.setVisibility(View.GONE);
        toolbarTitle.setText("首页");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeAdapter(getContext());

        headerView = View.inflate(getContext(), R.layout.header_home, null);
        circleViewPager = (CircleViewPager) headerView.findViewById(R.id.cvp_header);
        List<String> mUrlList = new ArrayList<>();
        mUrlList.add("http://d.5857.com/gqyhx_131102/004.jpg");
        mUrlList.add("http://img2.imgtn.bdimg.com/it/u=1563338466,3557560859&fm=214&gp=0.jpg");
        mUrlList.add("http://attachments.gfan.com/forum/201501/31/2123227t3eheezfvte0e0u.jpg");
        mUrlList.add("http://img2.imgtn.bdimg.com/it/u=872760111,3711017955&fm=214&gp=0.jpg");
        mUrlList.add("http://img2.niutuku.com/desk/1208/1922/ntk-1922-39448.jpg");
        circleViewPager.setUrlList(mUrlList);
        mAdapter.addHeaderView(headerView);

        mAdapter.setOnItemClickListener((position) -> StartReadActivity.start(getContext()));


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
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getHomePageData(page,rows)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<HomePageBean>>>(this, showLoading) {

                    @Override
                    public void onSuccess(BasicResponse<List<HomePageBean>> response) {
                        List<HomePageBean> result = response.getResult();
                        mAdapter.fillList(result);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }
}
