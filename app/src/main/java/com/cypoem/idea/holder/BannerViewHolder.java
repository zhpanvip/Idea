package com.cypoem.idea.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.net.IdeaApiService;
import com.zhpan.viewpager.holder.ViewHolder;

/**
 * Created by zhpan on 2017/12/6.
 */

public class BannerViewHolder implements ViewHolder<Integer> {
    private ImageView mImageView;
    @Override
    public View createView(Context context, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, null);
        mImageView=view.findViewById(R.id.iv_banner);
        return view;
    }

    @Override
    public void onBind(Context context, Integer drawable, int position, int size) {
        mImageView.setBackgroundResource(drawable);
       // ImageLoaderUtil.loadImg(mImageView, IdeaApiService.HOST+bannersBean.getBanner_pic());
    }
}
