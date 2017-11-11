package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;

/**
 * Created by zhpan on 2017/11/11.
 */

public class AdapterGvFind extends BaseAdapter<AdapterGvFind.FindGvBean, AdapterGvFind.FindGvHolder> {


    public AdapterGvFind(Context context) {
        super(context);
    }

    @Override
    public FindGvHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new FindGvHolder(parent, R.layout.item_gv_find);
    }

    @Override
    public void bindCustomViewHolder(FindGvHolder holder, int position) {
        FindGvBean item = getItem(position);
        holder.mImageView.setBackgroundResource(item.getResId());
        holder.mTextView.setText(item.getText());
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }


    public  class FindGvHolder extends BaseHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public FindGvHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
            mImageView=getView(R.id.iv_gv_find);
            mTextView=getView(R.id.tv_gv_find);
        }
    }

    public static class FindGvBean {
        private Integer resId;
        private String text;

        public Integer getResId() {
            return resId;
        }

        public void setResId(Integer resId) {
            this.resId = resId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
