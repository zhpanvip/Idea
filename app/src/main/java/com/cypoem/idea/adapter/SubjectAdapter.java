package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.SubjectBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.ArrayList;

/**
 * Created by zhpan on 2017/5/14.
 */

public class SubjectAdapter extends BaseAdapter<SubjectBean.WritesBean, SubjectAdapter.HomeViewHolder> {
    private OnItemClickListener clickListener;


    public SubjectAdapter setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public SubjectAdapter(Context context) {
        super(context);
        fillList(new ArrayList<>());
    }

    @Override
    public HomeViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(parent, R.layout.item_common);
    }

    @Override
    public void bindCustomViewHolder(HomeViewHolder holder, int position) {
        SubjectBean.WritesBean item = getItem(position);
        holder.mTvTitle.setText(item.getWrite_name());
        holder.mTvTime.setText(item.getDelivery_time().substring(0, 10));
        holder.mTvDetails.setText("赞" + item.getLike_count() + "/阅读" + item.getSection_count() + "/章节" + item.getSection_count());
        ImageLoaderUtil.loadImg(holder.imageView, IdeaApiService.HOST + item.getPic(), R.drawable.image_replace);
        //  item点击事件
        holder.mRelativeLayout.setOnClickListener((View v) -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });

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
