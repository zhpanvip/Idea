package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.ArticleWebViewActivity;
import com.cypoem.idea.activity.AuthorInfoActivity;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.constants.Constants;
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
        holder.mTvAll.setOnClickListener((View v) -> {
            ArticleWebViewActivity.start(mContext, "阅读", "http://www.baidu.com");
        });


        ArticleBean articleBean = mList.get(position);
        ArticleBean.UserBean user = articleBean.getUser();
        holder.mTvTitle.setText(articleBean.getSection_name());
        holder.mTvName.setText(user.getPen_name());
        holder.mTvAuther.setText(user.getPen_name());
        holder.mTvTime.setText(articleBean.getCreate_time());
        holder.mTvContent.setText(articleBean.getContent());
        ImageLoaderUtil.loadCircleImg(holder.mIvHeader, IdeaApiService.HOST+user.getIcon(), R.drawable.head_pic);

        holder.mTvFocus.setOnClickListener((View v) -> {
           // Toast.makeText(mContext, "关注", Toast.LENGTH_SHORT).show();
            if(user.isFollow()){
                cancelFocus(user.getUid());
            }else {
                addFocus(user.getUid());
            }

        });

        holder.mRlAuthor.setOnClickListener((View v) -> {
            AuthorInfoActivity.start(mContext,user.getUid());
        });
    }

    private void addFocus(String focusId){
        IdeaApi.getApiService()
                .addFocus(UserInfoTools.getUserId(mContext),focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(((StartReadActivity)mContext),true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
                    }
                });
    }

    private void cancelFocus(String focusId){
        IdeaApi.getApiService()
                .cancelFocus(UserInfoTools.getUserId(mContext),focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>((StartReadActivity)mContext,true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
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


        StartReadViewHolder(View itemView) {
            super(itemView);
            mTvAll = (TextView) itemView.findViewById(R.id.tv_all);
            mRlAuthor = (RelativeLayout) itemView.findViewById(R.id.rl_author);
            mTvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
            mIvHeader = (ImageView) itemView.findViewById(R.id.iv_head_pic);
            mTvTitle= (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuther = (TextView) itemView.findViewById(R.id.tv_author);
            mTvContent= (TextView) itemView.findViewById(R.id.tv_article);
            mTvTime= (TextView) itemView.findViewById(R.id.tv_time);
            mTvName= (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
