package com.cypoem.idea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cypoem.idea.R;
import com.cypoem.idea.view.MScrollView;

import butterknife.BindView;


public class FindNewFragment extends BaseFragment implements MScrollView.OnScrollChangedListener {

    @BindView(R.id.gv_item)
    GridView gvItem;
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
    }

    private void setListener() {
        ViewTreeObserver vto = mIvBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> mBannerHeight = mIvBanner.getHeight());
        scrollView.setOnScrollChangedListener(this);

    }

    @Override
    public void onScrollChanged(MScrollView scrollView, int y) {
        if (y < 3) {
            mLlSearchBar.setBackgroundColor(Color.argb(0, 255, 255, 255));
            mLine.setBackgroundColor(Color.argb(0, 208, 208, 208));
        } else if (y > 3 && y < mBannerHeight) {
            float scale = y / mBannerHeight;
            if (scale <= 1) {
                int alpha = (int) (255 * scale);
                Log.e("Main", "Alpha-----------" + alpha);
                mLlSearchBar.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                mLine.setBackgroundColor(Color.argb(alpha, 208, 208, 208));
            }
        } else if(y<1.5*mBannerHeight){
            mLlSearchBar.setBackgroundColor(Color.argb(255, 255, 255, 255));
            mLine.setBackgroundColor(Color.argb(255, 208, 208, 208));
        }
    }
}
