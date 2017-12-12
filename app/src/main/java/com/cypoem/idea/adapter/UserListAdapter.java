package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.view.SexView;

import java.util.List;

/**
 * Created by zhpan on 2017/12/12.
 */

public class UserListAdapter extends SearchAdapter<UserBean, UserListAdapter.ViewHolder> {


    public UserListAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_user);
    }

    @Override
    public void bindCustomViewHolder(ViewHolder holder, int position) {
        UserBean userBean = getItem(position);
        holder.mTvName.setText(userBean.getPen_name());
        holder.mTvStoryCount.setText(String.valueOf(userBean.getCreate_count()));
        holder.mTvFans.setText(String.valueOf(userBean.getWatchMeCount()));
        ImageLoaderUtil.loadCircleImg(holder.mIvHead, IdeaApiService.HOST+userBean.getIcon(),R.drawable.login_photo);
        if (userBean.getWatch_status() == 1) {
            holder.mTvFocus.setText("已关注");
        } else {
            holder.mTvFocus.setText("关注");
        }
    }

    @Override
    public int getItemCount() {
        List<UserBean> list = getList();
        return list.size()>3?3:list.size();
    }


    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseHolder {
        ImageView mIvHead;
        TextView mTvName;
        TextView mTvStoryCount;
        TextView mTvFans;
        TextView mTvFocus;
        SexView mSexView;

        public ViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            mIvHead = getView(R.id.iv_head);
            mTvFans = getView(R.id.tv_fans_count);
            mTvStoryCount = getView(R.id.tv_story_count);
            mTvName = getView(R.id.tv_name);
            mSexView = getView(R.id.sex_view);
            mTvFocus = getView(R.id.tv_follow);
        }
    }
}
