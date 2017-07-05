package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.net.IdeaApiService;

/**
 * Created by zhpan on 2017/5/14.
 *
 */

public class HomeAdapter extends BaseAdapter<HomePageBean,HomeViewHolder> {
    private OnItemClickListener clickListener;

    public HomeAdapter setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener=clickListener;
        return this;
    }

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public HomeViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(parent, R.layout.item_common);
    }

    @Override
    public void bindCustomViewHolder(HomeViewHolder holder, int position) {
        HomePageBean item = getItem(position);
        holder.mTvTitle.setText(item.getWrite_name());
        holder.mTvTime.setText(item.getDelivery_time().substring(0,10)+"/"+item.getUser().getPen_name());
        holder.mTvDetails.setText("赞"+item.getLike_count()+"/阅读"+item.getRead_count()+"/章节"+item.getSection_count());
        ImageLoaderUtil.loadRoundImg(holder.imageView, IdeaApiService.HOST+item.getPic(),R.drawable.img_placeholder);

        holder.mRelativeLayout.setOnClickListener((View v)-> {
                if(clickListener!=null){
                    clickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
