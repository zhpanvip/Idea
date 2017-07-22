package com.cypoem.idea.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.EverydayReBackBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/7/9.
 */

public class EverydayAdapter extends RecyclerView.Adapter<EverydayAdapter.EverydayViewHolder> {
    private Context mContext;
    private List<EverydayReBackBean> mList;

    public List<EverydayReBackBean> getList() {
        return mList;
    }

    public void setList(List<EverydayReBackBean> data) {
        this.mList = data;
    }

    public EverydayAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public EverydayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article, parent, false);
        return new EverydayViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(EverydayViewHolder holder, int position) {
        EverydayReBackBean everyday = mList.get(position);
        holder.mTvTime.setText(everyday.getPublish_time().substring(0,10));
        holder.mTvContent.setText(everyday.getContent());
        if(!TextUtils.isEmpty(everyday.getPhoto()))
        Glide.with(mContext).load(IdeaApiService.HOST+everyday.getPhoto()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.mIvPic.setImageBitmap(resource);
                Drawable drawable =new BitmapDrawable(resource);
                holder.mLlBackground.setBackground(drawable);
            }
        });
        //ImageLoaderUtil.loadImg(holder.mIvPic,everyday.getPhoto());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class EverydayViewHolder extends RecyclerView.ViewHolder {
        private View mLlBackground;
        private TextView mTvPublish;
        private TextView mTvTime;
        private TextView mTvContent;
        private ImageView mIvPic;

        EverydayViewHolder(View itemView) {
            super(itemView);
            mTvTime= (TextView) itemView.findViewById(R.id.tv_time);
            mTvContent= (TextView) itemView.findViewById(R.id.tv_content);
            mLlBackground=itemView.findViewById(R.id.ll_splash);
            mTvPublish= (TextView) itemView.findViewById(R.id.tv_publish);
            mIvPic= (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}
