package com.cypoem.idea.activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.WriteBean;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;

/**
 * Created by zhpan on 2017/12/20.
 */

public class ArticleAdapter extends BaseAdapter<WriteBean, ArticleAdapter.ViewHolder> {

    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_article);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        WriteBean writeBean = getItem(position);
        UserBean user = writeBean.getUser();
        holder.mTvTitle.setText(writeBean.getWrite_name());
        holder.mTvLabel.setText(writeBean.getType());
        holder.mTvName.setText(user.getPen_name());
        holder.mTvDate.setText(writeBean.getDelivery_time());
        holder.mTvContent.setText(writeBean.getIntroduction());
        holder.mTvPriseCount.setText(String.valueOf(writeBean.getLike_count()));
        holder.mTvReadCount.setText(String.valueOf(writeBean.getRead_count()));
        ImageLoaderUtil.loadCircleImg(holder.mIvHead, IdeaApiService.HOST + user.getIcon(), R.drawable.login_photo);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseHolder {
        ImageView mIvHead;
        TextView mTvTitle;
        TextView mTvLabel;
        TextView mTvName;
        TextView mTvDate;
        TextView mTvContent;
        TextView mTvReadCount;
        TextView mTvPriseCount;

        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mIvHead = getView(R.id.iv_head);
            mTvTitle = getView(R.id.tv_title);
            mTvName = getView(R.id.tv_name);
            mTvLabel = getView(R.id.tv_label);
            mTvDate = getView(R.id.tv_time);
            mTvContent = getView(R.id.tv_content);
            mTvReadCount = getView(R.id.tv_read_count);
            mTvPriseCount = getView(R.id.tv_prise_count);
        }
    }
}
