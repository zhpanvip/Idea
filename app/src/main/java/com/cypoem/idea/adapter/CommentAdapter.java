package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CommentBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/7/30.
 */

public abstract class CommentAdapter extends BaseAdapter<CommentBean, CommentAdapter.CommentHolder> {


    public CommentAdapter(Context context) {
        super(context);
    }

    public CommentAdapter(Context context, List<CommentBean> list) {
        super(context, list);
    }

    @Override
    public CommentHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent, R.layout.item_comment);
    }

    @Override
    public void bindCustomViewHolder(CommentHolder holder, int position) {
        CommentBean item = getItem(position);
        holder.tvContent.setText(item.getContent());
        holder.tvName.setText(item.getUser().getPen_name());
        holder.tvTime.setText(item.getTime());
        int like_count = item.getLike_count();
        holder.tvCount.setText(String.valueOf(item.getLike_count()));
        ImageLoaderUtil.loadCircleImg(holder.ivHead, IdeaApiService.HOST + item.getUser().getIcon(), R.drawable.login_photo);
        int like_status = item.getLike_status();
        holder.ivLike.setImageLevel(like_status);
        holder.ivLike.setOnClickListener((View v) -> {
            if(like_status==0){ //  取消点赞
                lightComment(item, holder.ivLike,1);
            }else { //  点赞

                lightComment(item, holder.ivLike,0);
            }
        });
    }

    public abstract void lightComment(CommentBean commentBean, ImageView imageView,int status);

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class CommentHolder extends BaseHolder {
        private TextView tvName;
        private TextView tvTime;
        private ImageView ivHead;
        private ImageView ivLike;
        private TextView tvCount;
        private TextView tvContent;

        public CommentHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
            tvName = getView(R.id.tv_name);
            tvTime = getView(R.id.tv_time);
            ivHead = getView(R.id.iv_head_pic);
            ivLike = getView(R.id.iv_like);
            tvCount = getView(R.id.tv_count);
            tvContent = getView(R.id.tv_content);
        }
    }
}
