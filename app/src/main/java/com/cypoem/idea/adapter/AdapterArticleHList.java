package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.WriteBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/11/11.
 */

public class AdapterArticleHList extends BaseAdapter<WriteBean, AdapterArticleHList.ArticleHHolder> {


    public AdapterArticleHList(Context context) {
        super(context);
    }

    @Override
    public ArticleHHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ArticleHHolder(parent, R.layout.item_article_hori_list);
    }

    @Override
    public void bindCustomViewHolder(ArticleHHolder holder, int position) {
        WriteBean writesBean = getItem(position);
        holder.mTvTitle.setText(writesBean.getWrite_name());
        UserBean user = writesBean.getUser();
        holder.mTvName.setText(user.getPen_name());
        holder.mTvDate.setText(writesBean.getDelivery_time());
       // holder.mTvLabel.setText(writesBean.getStoryLables().get(0).getLableName());
        holder.mTvContent.setText(writesBean.getIntroduction());
        holder.mTvReadCount.setText(String.valueOf(writesBean.getRead_count()));
        holder.mTvContinueCount.setText(String.valueOf(writesBean.getWriteStoryCount()));
        holder.mTvPraise.setText(String.valueOf(writesBean.getLike_count()));
        ImageLoaderUtil.loadImg(holder.mIvBackground, IdeaApiService.HOST + writesBean.getPic());
       /* if (TextUtils.isEmpty(writesBean.getPic())) {
           // holder.mIvBackground.setVisibility(View.GONE);
        } else {
            ImageLoaderUtil.loadImg(holder.mIvBackground, IdeaApiService.HOST + writesBean.getPic());
        }*/

    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ArticleHHolder extends BaseHolder {
        TextView mTvName;
        TextView mTvTitle;
        TextView mTvDate;
        TextView mTvLabel;
        ImageView mIvBackground;
        TextView mTvContent;
        TextView mTvContinueCount;
        TextView mTvReadCount;
        TextView mTvPraise;

        public ArticleHHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
            mTvName = getView(R.id.tv_name);
            mTvTitle = getView(R.id.tv_title);
            mTvDate = getView(R.id.tv_date);
            mTvLabel = getView(R.id.tv_label);
            mIvBackground = getView(R.id.iv_background);
            mTvContent = getView(R.id.tv_content);
            mTvContinueCount = getView(R.id.tv_rewrite_count);
            mTvReadCount = getView(R.id.tv_read_count);
            mTvPraise = getView(R.id.tv_prise);
        }
    }

}
