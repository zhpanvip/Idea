package com.cypoem.idea.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airong.core.recycler.BaseHolder;
import com.cypoem.idea.R;

/**
 * Created by zhpan on 2017/5/14.
 *
 */

public class HomeViewHolder extends BaseHolder {
    public RelativeLayout mRelativeLayout;
    public ImageView imageView;
    public TextView mTvTitle;
    public TextView mTvDescribe;
    public TextView mTvTime;
    public TextView mTvDetails;
    public HomeViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
        imageView=getView(R.id.iv_background);
        mTvTitle=getView(R.id.tv_title);
        mTvTime=getView(R.id.tv_time);
        mTvDescribe=getView(R.id.tv_describe);
        mTvDetails=getView(R.id.tv_details);
        mRelativeLayout=getView(R.id.rl_item_main);
    }


}
