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

import com.cypoem.idea.R;
import com.cypoem.idea.activity.ArticleWebViewActivity;
import com.cypoem.idea.activity.AuthorInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/29.
 */

public class OpusAdapter extends RecyclerView.Adapter<OpusAdapter.StartReadViewHolder> {
    private List<String> mList=new ArrayList<>();
    private Context mContext;
    private String fromWhere;
    public static final String OPUS="opus";
    public static final String START_READ="startRead";
    public OpusAdapter(Context mContext,String fromWhere) {
        this.mContext = mContext;
        this.fromWhere =fromWhere;
    }

    @Override
    public StartReadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View convertView=null;
        if(OPUS.equals(fromWhere)){
             convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article,
                    viewGroup, false);
        }else if(START_READ.equals(fromWhere)){
             convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_list,
                    viewGroup, false);
        }

        StartReadViewHolder holder = new StartReadViewHolder(convertView);

        return holder;

    }

    @Override
    public void onBindViewHolder(StartReadViewHolder holder, int position) {
        holder.mTvAll.setOnClickListener((View v) ->{
                ArticleWebViewActivity.start(mContext,"阅读","http://www.baidu.com");
        });
        holder.mRlAuthor.setOnClickListener((View v)->{
            AuthorInfoActivity.start(mContext);
        });
        holder.mTvFocus.setOnClickListener((View v)->{
            Toast.makeText(mContext, "关注", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {

        return 5;
    }


    // 可复用的VH
    public class StartReadViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mRlAuthor;
        public TextView mTvAll;
        public TextView mTvFocus;


        public StartReadViewHolder(View itemView) {
            super(itemView);
            mTvAll= (TextView) itemView.findViewById(R.id.tv_all);
            mRlAuthor= (RelativeLayout) itemView.findViewById(R.id.rl_author);
            mTvFocus= (TextView) itemView.findViewById(R.id.tv_focus);
        }
    }
}
