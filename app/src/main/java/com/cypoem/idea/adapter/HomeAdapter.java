package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.Article;

/**
 * Created by zhpan on 2017/5/14.
 */

public class HomeAdapter extends BaseAdapter<Article,HomeViewHolder> {


    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public HomeViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(parent, R.layout.item_common);
    }

    @Override
    public void bindCustomViewHolder(HomeViewHolder holder, int position) {
        Article item = getItem(position);
        holder.mTvTitle.setText("哈哈哈");
        ImageLoaderUtil.loadRoundImg(holder.imageView,item.getPicUrl(),R.drawable.img_placeholder);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }
}
