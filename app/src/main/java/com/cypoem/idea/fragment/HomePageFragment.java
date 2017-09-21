package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.BasicWebViewActivity;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.activity.SubjectActivity;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.BannerBean;
import com.cypoem.idea.module.bean.BaseOpusBean;
import com.cypoem.idea.module.bean.HomeBean;
import com.cypoem.idea.module.bean.SubjectBean;
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
    private int page = 1;
    private final int ROWS = 10;
    private CircleViewPager bannerView;

    public static HomePageFragment getFragment() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPtr(true);
        // getData(true, page);
        bannerView.setPtrFrame(mPtrFrame);
    }

    private void initData() {
        toolbarSubtitle.setVisibility(View.GONE);
        toolbarTitle.setText("首页");
        setRecycler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  销毁Activity时移除runnable防止内存泄露
        bannerView.stopCircleViewPager();
    }

    private void setRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeAdapter(getContext());
        mAdapter.fillList(new ArrayList<>());
       // mAdapter.setSubjectList(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position) -> {
            BaseOpusBean baseOpusBean = mAdapter.getList().get(position - 1);
            if(baseOpusBean instanceof HomeBean.WritesBean){
                HomeBean.WritesBean writesBean=(HomeBean.WritesBean)baseOpusBean;
                String writeId = String.valueOf(writesBean.getWrite_id());
                String authorId = writesBean.getUser().getUser_id();
                String title = writesBean.getWrite_name();
                StartReadActivity.start(getContext(), writeId, authorId, title);
            }else if(baseOpusBean instanceof HomeBean.SubjectsBean){
                HomeBean.SubjectsBean subjectsBean=( HomeBean.SubjectsBean)baseOpusBean;
                SubjectActivity.start(getContext(),subjectsBean.getSubject_id());
            }

        });
        initViewPager();
    }

    private void initViewPager() {
        View headerView = View.inflate(getContext(), R.layout.header_home, null);
        bannerView = (CircleViewPager) headerView.findViewById(R.id.cvp_header);
        bannerView.setInterval(5000);
        bannerView.setUrlList(new ArrayList<>());
        bannerView.setOnPageClickListener((int position) -> {
            List<BannerBean> urlList = bannerView.getUrlList();
            BannerBean bannerBean = urlList.get(position);
            int type = bannerBean.getType();
            if (type == 0) {
                BasicWebViewActivity.start(getContext(), "创意说", bannerBean.getUrl());
            } else {
                BannerBean.WriteBean write = bannerBean.getWrite();
                StartReadActivity.start(getContext(), write.getWrite_id(),write.getUser_id() , "创意说");
            }
        });
        mAdapter.addHeaderView(headerView);

        // getBannerData();
        /*bannerView.setDuration(5000);
        List<String> mUrlList = new ArrayList<>();
        mUrlList.add("http://d.5857.com/gqyhx_131102/004.jpg");
        mUrlList.add("http://img2.imgtn.bdimg.com/it/u=1563338466,3557560859&fm=214&gp=0.jpg");
        mUrlList.add("http://attachments.gfan.com/forum/201501/31/2123227t3eheezfvte0e0u.jpg");
        mUrlList.add("http://img2.imgtn.bdimg.com/it/u=872760111,3711017955&fm=214&gp=0.jpg");
        mUrlList.add("http://img2.niutuku.com/desk/1208/1922/ntk-1922-39448.jpg");
        // 设置数据
        bannerView.setPages(mUrlList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });*/
    }

    private void getBannerData() {
        IdeaApi.getApiService()
                .getBanner("1", "4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<BannerBean>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<List<BannerBean>> response) {
                        bannerView.getUrlList().clear();
                        bannerView.getUrlList().addAll(response.getResult());
                        bannerView.notifyDataChanged();
                    }
                });
    }


    @Override
    public void onPtrLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed((() -> getData(false, page)), 100);
    }

    @Override
    public void onPtrRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        frame.postDelayed((() -> {
            getData(true, page);
            getBannerData();
        }), 100);
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void getData(boolean isRefresh, int currentPage) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getHomeData(currentPage, ROWS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<HomeBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<HomeBean> response) {
                        if (isRefresh) {
                            mAdapter.getList().clear();
                        }
                        page++;
                        updateList(response.getResult());
                    }
                });
    }

    private void updateList(HomeBean result) {
        List<BaseOpusBean> list = mAdapter.getList();
        List<HomeBean.WritesBean> writeList = result.getWrites();
        if (writeList.size() < ROWS) {
            mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
        } else {
            mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        }
        list.addAll(result.getSubjects());
        list.addAll(writeList);
        //mAdapter.notifyDataSetChanged();
    }


    /*public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            ImageLoaderUtil.loadImg(mImageView, data);
        }
    }*/
}
