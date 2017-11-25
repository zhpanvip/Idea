package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CircleBean;

/**
 * Created by zhpan on 2017/11/25.
 */

public class CircleAdapter extends BaseAdapter<CircleBean,CircleAdapter.CircleViewHolder> {


    public CircleAdapter(Context context) {
        super(context);
    }

    @Override
    public CircleViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {

        return new CircleViewHolder(parent, R.layout.item_circle);
    }

    @Override
    public void bindCustomViewHolder(CircleViewHolder holder, int position) {

    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class CircleViewHolder extends BaseHolder{

        public CircleViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
        }
    }
}
