package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.net.IdeaApiService;

/**
 * Created by zhpan on 2017/12/10.
 */

public class AdapterNewest extends BaseAdapter<DiscoverBean.HostWritesBean, AdapterNewest.ViewHolder> {
    private Context mContext;

    public AdapterNewest(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.layout_newest);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        DiscoverBean.HostWritesBean writesBean = getItem(position);
        holder.mTvTitle.setText(writesBean.getWrite_name());
        holder.mTvName.setText(writesBean.getUser().getPen_name());
        ImageLoaderUtil.loadImg(holder.mIvBackground, IdeaApiService.HOST+writesBean.getPic());
        holder.mTvContent.setText(writesBean.getIntroduction());
        holder.mTvReadCount.setText(String.valueOf(writesBean.getRead_count()));
        holder.mTvLikeCount.setText(String.valueOf(writesBean.getLike_count()));
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseHolder {
        TextView mTvTitle;
        TextView mTvName;
        ImageView mIvBackground;
        TextView mTvContent;
        TextView mTvLikeCount;
        TextView mTvReadCount;

        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mTvTitle = getView(R.id.tv_title);
            mTvName = getView(R.id.tv_name);
            mIvBackground = getView(R.id.iv_background);
            mTvContent = getView(R.id.tv_content);
            mTvLikeCount = getView(R.id.tv_like_count);
            mTvReadCount = getView(R.id.tv_read_count);
        }
    }
}
