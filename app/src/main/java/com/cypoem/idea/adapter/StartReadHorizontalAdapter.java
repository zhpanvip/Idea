package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cypoem.idea.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/29.
 */

public class StartReadHorizontalAdapter extends RecyclerView.Adapter<StartReadHorizontalAdapter.StartReadViewHolder> {
    private List<String> mList=new ArrayList<>();
    private Context mContext;

    public StartReadHorizontalAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public StartReadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_article,
                viewGroup, false);
        StartReadViewHolder viewHolder = new StartReadViewHolder(view);


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(StartReadViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 5;
    }


    // 可复用的VH
    public class StartReadViewHolder extends RecyclerView.ViewHolder {
        public ImageView picImageView;
        public TextView picUrl;

        public StartReadViewHolder(View itemView) {
            super(itemView);

        }
    }
}
