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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.BaseActivity;
import com.cypoem.idea.activity.BasicWebViewActivity;
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
import com.cypoem.idea.adapter.AdapterNewest;
import com.cypoem.idea.adapter.AdapterRecommend;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.holder.BannerViewHolder;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.DiscoverBean;
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
    @BindView(R.id.iv_hot_arrow)
    ImageView ivHotArrow;
    @BindView(R.id.scroll_view)
    MScrollView scrollView;
    @BindView(R.id.ll_search_bar)
    LinearLayout mLlSearchBar;
    @BindView(R.id.cvp_banner)
    CircleViewPager mViewPager;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.rv_newest)
    RecyclerView mRvNewest;/*
    @BindView(R.id.ll_hot_author)
    LinearLayout mLlAuthor;*/
    @BindView(R.id.srl)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.line_recommend)
    View mLineRecommend;
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
        View hotRecommend = rootView.findViewById(R.id.rl_recommend);
        hotRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //  设置最新故事的title
        View newestTitle = rootView.findViewById(R.id.ll_title_newest);

        newestTitle.findViewById(R.id.iv_title_icon).setBackgroundResource(R.drawable.t2_new);
        ((TextView) newestTitle.findViewById(R.id.tv_title)).setText("最新故事");
        newestTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("最新故事");
            }
        });

        //  设置热门作者title
        View hotAuthor = rootView.findViewById(R.id.rl_hot_author);
        hotAuthor.findViewById(R.id.iv_title_icon).setBackgroundResource(R.drawable.t2_fire);
        ((TextView) hotAuthor.findViewById(R.id.tv_title)).setText("热门作者");
        hotAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("热门作者");
            }
        });

        setGridView();
        setNew();
    }


    //  最新
    private void setNew() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvNewest.setLayoutManager(linearLayoutManager);
        AdapterArticleHList adapter = new AdapterArticleHList(getContext());
        mRvNewest.setAdapter(adapter);
        mRvNewest.setNestedScrollingEnabled(false);
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
    private void setUserList(List<DiscoverBean.UsersBean> list) {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_follow_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterAuthorHList adapter = new AdapterAuthorHList(getContext());
        adapter.fillList(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(position -> {
            DiscoverBean.UsersBean usersBean = list.get(position);
            AuthorInfoActivity.start(getContext(), usersBean.getUser_id() + "");
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
        /*Animation animation = new AlphaAnimation(1.0f, 0f);
        animation.setDuration(500);
        mLlSearchBar.setAnimation(animation);*/
        mLlSearchBar.setVisibility(View.GONE);
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.stopLoop();
    }

    //  请求数据
    private void getData() {
        IdeaApi.getApiService()
                .getDiscover(UserInfoTools.getUserId(getContext()), page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<DiscoverBean>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<DiscoverBean> response) {
                        //refreshLayout.setRefreshing(false);
                        DiscoverBean result = response.getResult();
                        getDataSuccess(result);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        refreshLayout.setRefreshing(false);
                        Animation animation = new AlphaAnimation(0.1f, 1.0f);
                        animation.setDuration(500);
                        mLlSearchBar.setAnimation(animation);
                        mLlSearchBar.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void getDataSuccess(DiscoverBean result) {
        setUserList(result.getUsers());
        setRecommend(result.getHostCircles());
        setHotOpus(result.getHostWrites());
    }

    //  热门推荐
    private void setRecommend(List<DiscoverBean.HostCirclesBean> hostWrites) {
        if (hostWrites == null || hostWrites.size() == 0) {
            mLineRecommend.setVisibility(View.GONE);
            return;
        } else {
            mLineRecommend.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRecommend.setLayoutManager(linearLayoutManager);
        mRvRecommend.setNestedScrollingEnabled(false);
        AdapterRecommend adapter = new AdapterRecommend(getContext());
        adapter.fillList(hostWrites);
        mRvRecommend.setAdapter(adapter);
    }

    //  热门作品
    private void setHotOpus(List<DiscoverBean.HostWritesBean> hostWrites) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNewest.setLayoutManager(linearLayoutManager);
        AdapterNewest adapter = new AdapterNewest(getContext());
        adapter.fillList(hostWrites);
        mRvNewest.setAdapter(adapter);
        mRvNewest.setNestedScrollingEnabled(false);

    }


    protected void setBanner() {
        List<Integer> mListInt = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            int drawable = getResources().getIdentifier("banner" + i, "drawable", getContext().getPackageName());
            mListInt.add(drawable);
        }
        mViewPager.setIndicatorRadius(4);
        mViewPager.setCanLoop(true);
        mViewPager.setAutoPlay(true);
        mViewPager.setInterval(4 * 1000);
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
                BaseActivity.start(getContext(), activity, bundle);
                break;
            case 1:
                activity = CreateEveryDayActivity.class;
                bundle.putString("title", "每日一句");
                BaseActivity.start(getContext(), activity, bundle);
                break;
            case 2:
                ReadMeetingActivity.start(getContext(), "", Constants.READ_MEETING);
                break;
            case 3:
                activity = NearbyActivity.class;
                bundle.putString("title", "附近");
                BaseActivity.start(getContext(), activity, bundle);
                break;

            case 4:
                activity = HotStoryActivity.class;
                bundle.putString("title", "热门故事");
                BaseActivity.start(getContext(), activity, bundle);
                break;
            case 5:
                activity = WeekSelectActivity.class;
                bundle.putString("title", "一周精选");
                BaseActivity.start(getContext(), activity, bundle);
                break;
            case 6:
                activity = EssayCompetitionActivity.class;
                bundle.putString("title", "有奖征文");
                BaseActivity.start(getContext(), activity, bundle);
                break;
        }

    }


}
