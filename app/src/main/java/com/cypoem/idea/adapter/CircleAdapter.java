package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CircleBean;
import com.cypoem.idea.module.bean.CircleListBean;
import com.cypoem.idea.net.IdeaApiService;

/**
 * Created by zhpan on 2017/11/25.
 */

public class CircleAdapter extends BaseAdapter<CircleListBean.CirclesBean,CircleAdapter.CircleViewHolder> {


    public CircleAdapter(Context context) {
        super(context);
    }

    @Override
    public CircleViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {

        return new CircleViewHolder(parent, R.layout.item_circle);
    }

    @Override
    public void bindCustomViewHolder(CircleViewHolder holder, int position) {
        CircleListBean.CirclesBean circlesBean = getItem(position);
        ImageLoaderUtil.loadImg( holder.mIvPic, IdeaApiService.HOST+circlesBean.getIcon());
        holder.mTvCircleName.setText(circlesBean.getName());
        holder.mLabel.setText(circlesBean.getType()+"");
        holder.mTvUpdateCount.setText(String.valueOf(circlesBean.getPublicStoryCount()));
        holder.mTvCircleName.setText(String.valueOf(circlesBean.getStoryCount()));
        holder.mTvFollowCount.setText(String.valueOf(circlesBean.getUserCount()));
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class CircleViewHolder extends BaseHolder{
        ImageView mIvPic;
        TextView mTvCircleName;
        TextView mLabel;
        TextView mTvStoryCount;
        TextView mTvFollowCount;
        TextView mTvUpdateCount;

        public CircleViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mIvPic=getView(R.id.iv_hot_pic);
            mTvCircleName=getView(R.id.tv_circle_name);
            mLabel=getView(R.id.tv_label);
            mTvStoryCount=getView(R.id.tv_story_count);
            mTvFollowCount=getView(R.id.tv_follow_count);
            mTvUpdateCount=getView(R.id.tv_update_count);
        }
    }
}
