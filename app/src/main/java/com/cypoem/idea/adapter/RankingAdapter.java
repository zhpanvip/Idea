package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.RankingBean;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;

/**
 * Created by zhpan on 2017/12/12.
 */

public class RankingAdapter extends BaseAdapter<RankingBean,RankingAdapter.ViewHolder> {
    private Context mContext;

    public RankingAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_ranking);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        RankingBean rankingBean = getItem(position);
        holder.mTvName.setText(rankingBean.getPen_name());
        holder.mTvRanking.setText("NO."+(position+4));
        holder.mTvValue.setText(rankingBean.getRollout_count());
        ImageLoaderUtil.loadCircleImg(holder.mIvHead, IdeaApiService.HOST+rankingBean.getIcon(),R.drawable.login_photo);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseHolder{
        TextView mTvRanking;
        TextView mTvName;
        ImageView mIvHead;
        TextView mTvValue;
        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mTvName=getView(R.id.tv_name);
            mTvRanking=getView(R.id.tv_ranking);
            mIvHead=getView(R.id.iv_head);
            mTvValue=getView(R.id.tv_value);
        }
    }
}
