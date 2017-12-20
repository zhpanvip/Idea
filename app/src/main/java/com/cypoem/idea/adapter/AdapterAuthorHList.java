package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.event.FollowSuccess;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.SexView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/11/11.
 */

public class AdapterAuthorHList extends BaseAdapter<UserBean, AdapterAuthorHList.AuthorHHolder> {
    private Context mContext;
    private FollowListener mFollowListener;

    public void setFollowListener(FollowListener followListener) {
        mFollowListener = followListener;
    }

    public interface FollowListener {
        void onFollowButtonClick(int position, int type);
    }

    public AdapterAuthorHList(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public AuthorHHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new AuthorHHolder(parent, R.layout.item_find_author);
    }

    @Override
    public void bindCustomViewHolder(AuthorHHolder holder, int position) {
        UserBean usersBean = getItem(position);
        holder.mTvName.setText(usersBean.getPen_name());
        ImageLoaderUtil.loadImg(holder.mIvHeader, IdeaApiService.HOST + usersBean.getIcon());

        int watch_status = usersBean.getWatch_status();
        double sex = Double.parseDouble(usersBean.getSex());
        holder.mSexView.setMalePercent(1 - sex);
        if (watch_status == 1) {
            holder.mBtn.setText("已关注");
            holder.mBtn.setOnClickListener(v -> mFollowListener.onFollowButtonClick(position, 0));
        } else {
            holder.mBtn.setText("关注");
            holder.mBtn.setOnClickListener(v -> mFollowListener.onFollowButtonClick(position, 1));
        }
        holder.mCardView.setOnClickListener(v -> clickListener.onItemClick(position));
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class AuthorHHolder extends BaseHolder {
        CircleImageView mIvHeader;
        SexView mSexView;
        TextView mTvName;
        TextView mTvAddress;
        Button mBtn;
        CardView mCardView;

        public AuthorHHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
            mBtn = getView(R.id.btn_follow);
            mIvHeader = getView(R.id.iv_header);
            mSexView = getView(R.id.sex_view);
            mTvAddress = getView(R.id.tv_address);
            mTvName = getView(R.id.tv_name);
            mCardView = getView(R.id.cv_author);
        }
    }
}
