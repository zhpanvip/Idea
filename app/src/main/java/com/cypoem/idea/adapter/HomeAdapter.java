package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.BaseOpusBean;
import com.cypoem.idea.module.bean.HomeBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/14.
 */

public class HomeAdapter extends BaseAdapter<BaseOpusBean, HomeAdapter.HomeViewHolder> {
    private OnItemClickListener clickListener;


    public HomeAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public HomeAdapter(Context context) {
        super(context);
        fillList(new ArrayList<>());
    }

    @Override
    public HomeViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(parent, R.layout.item_common);
    }

    @Override
    public void bindCustomViewHolder(HomeViewHolder holder, int position) {
        BaseOpusBean item = getItem(position);
        if (item instanceof HomeBean.WritesBean) {
            HomeBean.WritesBean writesBean = (HomeBean.WritesBean) item;
            holder.mTvTitle.setText(writesBean.getWrite_name());
            holder.mTvTime.setText(writesBean.getDelivery_time().substring(0, 10) + "/" + writesBean.getUser().getPen_name());
            holder.mTvDetails.setText("赞" + writesBean.getLike_count() + "/阅读" + writesBean.getRead_count() + "/章节" + writesBean.getSection_count());
            ImageLoaderUtil.loadImg(holder.imageView, IdeaApiService.HOST + writesBean.getPic(), R.drawable.image_replace);
            setVisible(holder);
        } else if (item instanceof HomeBean.SubjectsBean) {
            HomeBean.SubjectsBean subjectsBean = (HomeBean.SubjectsBean) item;
            ImageLoaderUtil.loadImg(holder.imageView, IdeaApiService.HOST + subjectsBean.getSubject_pic(), R.drawable.image_replace);

            setInVisible(holder);
        }
        //  item点击事件
        holder.mRelativeLayout.setOnClickListener((View v) -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    private void setInVisible(HomeViewHolder homeViewHolder) {
        homeViewHolder.mLlLabel.setVisibility(View.GONE);
        homeViewHolder.mTvTitle.setVisibility(View.GONE);
        homeViewHolder.mTvTime.setVisibility(View.GONE);
        homeViewHolder.mTvDetails.setVisibility(View.GONE);
        homeViewHolder.mIvShadow.setVisibility(View.GONE);
    }

    private void setVisible(HomeViewHolder homeViewHolder) {
        homeViewHolder.mLlLabel.setVisibility(View.VISIBLE);
        homeViewHolder.mTvTitle.setVisibility(View.VISIBLE);
        homeViewHolder.mTvTime.setVisibility(View.VISIBLE);
        homeViewHolder.mTvDetails.setVisibility(View.VISIBLE);
        homeViewHolder.mIvShadow.setVisibility(View.VISIBLE);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class HomeViewHolder extends BaseHolder {
        private RelativeLayout mRelativeLayout;
        private ImageView imageView;
        private TextView mTvTitle;
        private TextView mTvDescribe;
        private TextView mTvTime;
        private TextView mTvDetails;
        private LinearLayout mLlLabel;
        private ImageView mIvShadow;

        private HomeViewHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
            imageView = getView(R.id.iv_background);
            mTvTitle = getView(R.id.tv_title);
            mTvTime = getView(R.id.tv_time);
            mTvDescribe = getView(R.id.tv_describe);
            mTvDetails = getView(R.id.tv_details);
            mRelativeLayout = getView(R.id.rl_item_main);
            mLlLabel = getView(R.id.ll_label);
            mIvShadow = getView(R.id.iv_shadow);
        }
    }
}
