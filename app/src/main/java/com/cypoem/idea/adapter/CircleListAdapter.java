package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CircleBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/12/13.
 */

public class CircleListAdapter extends SearchAdapter<CircleBean, CircleListAdapter.ViewHolder> {


    public CircleListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_circle_info);
    }

    @Override
    public int getItemCount() {
        List<CircleBean> list = getList();
        return list.size()>3?3:list.size();
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        CircleBean circleBean = getItem(position);
        holder.mTvName.setText(circleBean.getName());
        holder.mTvStoryCount.setText(String.valueOf(circleBean.getShareCount()));
        holder.mTvFocus.setText(String.valueOf(circleBean.getUserCount()));
        ImageLoaderUtil.loadImg(holder.mIvHead, IdeaApiService.HOST+circleBean.getIcon());
        holder.mTvLabel.setText(circleBean.getType());
        if (circleBean.getStatus() == 1) {
            holder.mTvFocus.setText("关注");
        } else {
            holder.mTvFocus.setText("已关注");
        }
    }


    public static class ViewHolder extends BaseHolder {
        ImageView mIvHead;
        TextView mTvName;
        TextView mTvStoryCount;
        TextView mTvFans;
        TextView mTvFocus;
        TextView mTvLabel;

        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mIvHead = getView(R.id.iv_hot_pic);
           // mTvFans = getView(R.id.tv_fans_count);
            mTvStoryCount = getView(R.id.tv_story_count);
            mTvName = getView(R.id.tv_circle_name);
            mTvLabel = getView(R.id.tv_label);
            mTvFocus = getView(R.id.tv_follow_count);
        }
    }
}
