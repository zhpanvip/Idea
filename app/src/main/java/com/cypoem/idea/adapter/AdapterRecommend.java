package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/12/10.
 * 发现页面热门推荐RecyclerView的Adapter
 */

public class AdapterRecommend extends BaseAdapter<DiscoverBean.HostCirclesBean, AdapterRecommend.ViewHolder> {
    private Context mContext;

    public AdapterRecommend(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(parent, R.layout.layout_hot_recommend);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        DiscoverBean.HostCirclesBean hotCirclesBean = getItem(position);
        ImageLoaderUtil.loadImg(holder.mIvHeader, IdeaApiService.HOST + hotCirclesBean.getIcon());
        holder.mTvCircleName.setText(hotCirclesBean.getName());
        holder.mTvStoryCount.setText(String.valueOf(hotCirclesBean.getStoryCount()));
        holder.mTvFollowCount.setText(String.valueOf(hotCirclesBean.getShareCount()));
        List<DiscoverBean.HostCirclesBean.WritesBean> writes = hotCirclesBean.getWrites();
        if (writes == null || writes.size() == 0) {
            holder.mRecyclerView.setVisibility(View.GONE);
        } else {
            holder.mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.mRecyclerView.setLayoutManager(linearLayoutManager);

            AdapterArticleHList adapter = new AdapterArticleHList(mContext);
            adapter.fillList(writes);
            holder.mRecyclerView.setAdapter(adapter);
            holder.mRecyclerView.setNestedScrollingEnabled(true);
            int followStatus = hotCirclesBean.getStatus();
            if (followStatus == 1) {
                holder.mTvFollow.setText("关注");
            } else {
                holder.mTvFollow.setText("已关注");
            }
        }
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseHolder {
        ImageView mIvHeader;
        TextView mTvCircleName;
        TextView mTvStoryCount;
        TextView mTvFollowCount;
        TextView mTvFollow;
        RecyclerView mRecyclerView;

        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mIvHeader = getView(R.id.iv_hot_pic);
            mTvCircleName = getView(R.id.tv_circle_name);
            mTvStoryCount = getView(R.id.tv_story_count);
            mTvFollowCount = getView(R.id.tv_follow_count);
            mTvFollow = getView(R.id.tv_follow);
            mRecyclerView = getView(R.id.rv_hot_recommend);
        }
    }
}
