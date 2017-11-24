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

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.AdapterArticleHList;
import com.cypoem.idea.adapter.AdapterAuthorHList;
import com.cypoem.idea.adapter.AdapterGvFind;
import com.cypoem.idea.view.MScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FindNewFragment extends BaseFragment implements MScrollView.OnScrollChangedListener, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.iv_banner)
    ImageView mIvBanner;
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

    public static FindNewFragment getFragment() {
        return new FindNewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
        setData();
        setRefreshLayout();
    }

    private void setRefreshLayout() {
        refreshLayout.setProgressViewEndTarget(false, 300);
        /*refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
        refreshLayout.setOnRefreshListener(this);
    }

    private void setData() {
        setGridView();
        setRecommend();
        setNew();
        setUserList();
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
    }

    //  设置热门作者
    private void setUserList() {
        RecyclerView recyclerView = (RecyclerView) mLlAuthor.findViewById(R.id.rv_follow_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterAuthorHList adapter = new AdapterAuthorHList(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setListener() {
        ViewTreeObserver vto = mIvBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> mBannerHeight = mIvBanner.getHeight());
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
