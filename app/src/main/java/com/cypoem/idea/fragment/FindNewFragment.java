package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airong.core.recycler.BaseAdapter;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.BaseActivity;
import com.cypoem.idea.activity.CreateEveryDayActivity;
import com.cypoem.idea.activity.EssayCompetitionActivity;
import com.cypoem.idea.activity.HotStoryActivity;
import com.cypoem.idea.activity.NearbyActivity;
import com.cypoem.idea.activity.RankingListActivity;
import com.cypoem.idea.activity.ReadMeetingActivity;
import com.cypoem.idea.activity.SearchActivity;
import com.cypoem.idea.activity.WeekSelectActivity;
import com.cypoem.idea.adapter.AdapterArticleHList;
import com.cypoem.idea.adapter.AdapterAuthorHList;
import com.cypoem.idea.adapter.AdapterGvFind;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.holder.BannerViewHolder;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.MScrollView;
import com.zhpan.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class FindNewFragment extends BaseFragment implements MScrollView.OnScrollChangedListener, SwipeRefreshLayout.OnRefreshListener, BaseAdapter.OnItemClickListener {

    @BindView(R.id.gv_item)
    RecyclerView gvItem;
    @BindView(R.id.iv_hot_recommend)
    ImageView ivHotRecommend;
    @BindView(R.id.iv_hot_arrow)
    ImageView ivHotArrow;
    @BindView(R.id.iv_hot_pic)
    ImageView ivHotPic;
    @BindView(R.id.rv_hot_recommend)
    RecyclerView rvHotRecommend;
    @BindView(R.id.scroll_view)
    MScrollView scrollView;
    @BindView(R.id.ll_search_bar)
    LinearLayout mLlSearchBar;
    @BindView(R.id.cvp_banner)
    CircleViewPager mViewPager;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.ll_recommend)
    LinearLayout mLlRecommend;
    @BindView(R.id.ll_new)
    LinearLayout mLlNew;
    @BindView(R.id.ll_hot_author)
    LinearLayout mLlAuthor;
    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    //  Banner高度
    private float mBannerHeight;
    private int page = 1;

    public static FindNewFragment getFragment() {
        return new FindNewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setBanner();
        setListener();
        setData();
        setRefreshLayout(true);
    }

    private void setData() {
        setGridView();
        setRecommend();
        setNew();
    }

    //  热门推荐
    private void setRecommend() {
        RecyclerView recyclerView = (RecyclerView) mLlRecommend.findViewById(R.id.rv_hot_recommend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterArticleHList adapter = new AdapterArticleHList(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    //  最新
    private void setNew() {
        RecyclerView recyclerView = (RecyclerView) mLlNew.findViewById(R.id.rv_hot_recommend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterArticleHList adapter = new AdapterArticleHList(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    //  设置Banner下边的GridView
    private void setGridView() {
        List<AdapterGvFind.FindGvBean> list = new ArrayList<>();
        String[] mListGvText = getActivity().getApplication().getResources().getStringArray(R.array.gv_find_text);

        for (int i = 1; i <= 7; i++) {
            int resId = getResources().getIdentifier("t2_find_gv" + i, "drawable", getContext().getPackageName());
            AdapterGvFind.FindGvBean findGvBean = new AdapterGvFind.FindGvBean();
            findGvBean.setResId(resId);
            findGvBean.setText(mListGvText[i - 1]);
            list.add(findGvBean);
        }

        AdapterGvFind adapter = new AdapterGvFind(getContext());
        adapter.fillList(list);
        gvItem.setLayoutManager(new GridLayoutManager(getContext(), 4));
        gvItem.setAdapter(adapter);
        //  防止滑动卡顿
        gvItem.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(this);
    }

    //  设置热门作者
    private void setUserList( List<DiscoverBean.UsersBean> list) {
        RecyclerView recyclerView = (RecyclerView) mLlAuthor.findViewById(R.id.rv_follow_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterAuthorHList adapter = new AdapterAuthorHList(getContext());
        adapter.fillList(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<DiscoverBean.UsersBean> list1 = adapter.getList();
                DiscoverBean.UsersBean usersBean = list.get(position);
                AuthorInfoActivity.start(getContext(),usersBean.getUser_id()+"");
            }
        });
    }

    private void setListener() {
        ViewTreeObserver vto = mViewPager.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> mBannerHeight = mViewPager.getHeight());
        scrollView.setOnScrollChangedListener(this);
    }

    /**
     * ScrollView滚动监听
     *
     * @param scrollView ScrollView
     * @param y          y轴滚动距离
     */
    @Override
    public void onScrollChanged(MScrollView scrollView, int y) {
        if (y < 3 || y == 3) {
            mLlSearchBar.setBackgroundColor(Color.argb(0, 255, 255, 255));
            mLine.setBackgroundColor(Color.argb(0, 208, 208, 208));
        } else if (y > 3 && y < mBannerHeight) {
            float scale = y / mBannerHeight;
            if (scale <= 1) {
                int alpha = (int) (255 * scale);
                mLlSearchBar.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                mLine.setBackgroundColor(Color.argb(alpha, 208, 208, 208));
            }
        } else if (y < 1.5 * mBannerHeight) {
            mLlSearchBar.setBackgroundColor(Color.argb(255, 255, 255, 255));
            mLine.setBackgroundColor(Color.argb(255, 208, 208, 208));
        }
    }

    @Override
    public void onRefresh() {
        getData();
        Handler handler = new Handler();
        handler.postDelayed(() -> refreshLayout.setRefreshing(false), 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.stopLoop();
    }

    private void getData() {
        IdeaApi.getApiService()
                .getDiscover(UserInfoTools.getUserId(getContext()), page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<DiscoverBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<DiscoverBean> response) {
                        refreshLayout.setRefreshing(false);
                        DiscoverBean result = response.getResult();
                        getDataSuccess(result);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void getDataSuccess(DiscoverBean result) {
        List<DiscoverBean.UsersBean> users = result.getUsers();
        setUserList(result.getUsers());
    }


    protected void setBanner() {
        List<Integer> mListInt=new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            int drawable = getResources().getIdentifier("banner" + i, "drawable", getContext().getPackageName());
            mListInt.add(drawable);
        }
        mViewPager.setIndicatorRadius(4);
        mViewPager.setCanLoop(true);
        mViewPager.setAutoPlay(true);
        mViewPager.setInterval(4*1000);
        mViewPager.setIndicatorColor(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
        mViewPager.setPages(mListInt, () -> new BannerViewHolder());
    }

    @OnClick({R.id.ll_search_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search_bar:
                SearchActivity.start(getContext());
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Class<? extends BaseActivity> activity = null;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                activity = RankingListActivity.class;
                bundle.putString("title", "欣赏值排行榜");
                break;
            case 1:
                activity = CreateEveryDayActivity.class;
                bundle.putString("title", "每日一句");
                break;
            case 2:
                activity = ReadMeetingActivity.class;
                bundle.putString("title", "线下读书会");
                break;
            case 3:
                activity = NearbyActivity.class;
                bundle.putString("title", "附近");
                break;

            case 4:
                activity = HotStoryActivity.class;
                bundle.putString("title", "热门故事");
                break;
            case 5:
                activity = WeekSelectActivity.class;
                bundle.putString("title", "一周精选");
                break;
            case 6:
                activity = EssayCompetitionActivity.class;
                bundle.putString("title", "有奖征文");
                break;
        }
        BaseActivity.start(getContext(), activity, bundle);
    }
}
