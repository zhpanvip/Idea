package com.cypoem.idea.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.ArticleActivity;
import com.cypoem.idea.activity.ArticleWebViewActivity;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.LoginActivity;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/5/29.
 */

public class OpusAdapter extends RecyclerView.Adapter<OpusAdapter.StartReadViewHolder> {
    private List<ArticleBean> mList;
    private Context mContext;
    private String fromWhere;
    public static final String OPUS = "opus";
    public static final String START_READ = "startRead";
    private boolean isFocus;

    public OpusAdapter(Context mContext, String fromWhere) {
        this.mContext = mContext;
        this.fromWhere = fromWhere;
    }

    public List<ArticleBean> getList() {
        return mList;
    }

    public void setList(List<ArticleBean> mList) {
        this.mList = mList;
    }

    @Override
    public StartReadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View convertView = null;
        if (OPUS.equals(fromWhere)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article,
                    viewGroup, false);
        } else if (START_READ.equals(fromWhere)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_list,
                    viewGroup, false);
        }

        StartReadViewHolder holder = new StartReadViewHolder(convertView);

        return holder;

    }

    @Override
    public void onBindViewHolder(StartReadViewHolder holder, int position) {
        /*holder.mTvAll.setOnClickListener((View v) -> {
            ArticleWebViewActivity.start(mContext, "阅读", "http://www.baidu.com");
        });*/
        ArticleBean articleBean = mList.get(position);
        ArticleBean.UserBean user = articleBean.getUser();
        holder.mTvName.setText(user.getPen_name());

        int watch_status = articleBean.getWatch_status();
        int status = articleBean.getStatus();
        if (status == 0) {
            holder.mTvContent.setText("此章节已被作者删除，请自行脑补~");
        } else {
            holder.mTvTime.setText(articleBean.getCreate_time());
            holder.mTvTitle.setText(articleBean.getSection_name());
            holder.mTvContent.setText(articleBean.getContent());
            holder.mTvAuther.setText(user.getPen_name());

            holder.mFrameLayout.setOnClickListener((View v) -> {

                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra("article", articleBean.getContent());
                intent.putExtra("title", articleBean.getSection_name());
                intent.putExtra("author", articleBean.getUser().getPen_name());
                //context.startActivity(intent);
                mContext.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation((StartReadActivity) mContext
                                , holder.mLlText, "sharedView").toBundle());

            });
        }

        if (watch_status == 1) {
            isFocus = true;
            holder.mTvFocus.setText("已关注");
        } else {
            isFocus = false;
            holder.mTvFocus.setText("关注");
        }
        ImageLoaderUtil.loadCircleImg(holder.mIvHeader, IdeaApiService.HOST + user.getIcon(), R.drawable.head_pic);
        //  点击关注按钮
        holder.mTvFocus.setOnClickListener((View v) -> {
            if (UserInfoTools.getIsLogin(mContext)) {
                if (isFocus) {
                    cancelFocus(user.getUserId(), holder.mTvFocus);
                } else {
                    addFocus(user.getUserId(), holder.mTvFocus);
                }
            } else {
                LoginActivity.start(mContext);
            }


        });

        holder.mRlAuthor.setOnClickListener((View v) -> {
            AuthorInfoActivity.start(mContext, user.getUserId());
        });
    }

    private void addFocus(String focusId, TextView tvFocus) {
        IdeaApi.getApiService()
                .addFocus(UserInfoTools.getUserId(mContext), focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(((StartReadActivity) mContext), true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
                        isFocus = true;
                        tvFocus.setText("已关注");
                    }
                });
    }

    private void cancelFocus(String focusId, TextView tvFocus) {
        IdeaApi.getApiService()
                .cancelFocus(UserInfoTools.getUserId(mContext), focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>((StartReadActivity) mContext, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
                        isFocus = false;
                        tvFocus.setText("+关注");
                    }
                });
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }


    // 可复用的VH
    static class StartReadViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRlAuthor;
        private TextView mTvAll;
        private TextView mTvFocus;
        private ImageView mIvHeader;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvContent;
        private TextView mTvAuther;
        private TextView mTvName;
        private FrameLayout mFrameLayout;
        private LinearLayout mLlText;

        StartReadViewHolder(View itemView) {
            super(itemView);
            mTvAll = (TextView) itemView.findViewById(R.id.tv_all);
            mRlAuthor = (RelativeLayout) itemView.findViewById(R.id.rl_author);
            mTvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
            mIvHeader = (ImageView) itemView.findViewById(R.id.iv_head_pic);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuther = (TextView) itemView.findViewById(R.id.tv_author);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_article);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.fl_read);
            mLlText = (LinearLayout) itemView.findViewById(R.id.ll_text);
        }
    }
}
