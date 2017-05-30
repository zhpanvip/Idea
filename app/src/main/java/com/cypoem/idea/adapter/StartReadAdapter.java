package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/29.
 */

public class StartReadAdapter extends RecyclerView.Adapter<StartReadAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 100;

    private final Context mContext;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;

    public StartReadAdapter(Context context) {
        this(context, DEFAULT_ITEM_COUNT);
    }

    public StartReadAdapter(Context context, int itemCount) {
        mContext = context;
        mItems = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            addItem(i);
        }
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_read, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        OpusAdapter adapter = new OpusAdapter(mContext, OpusAdapter.START_READ);
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置显示布局的方向，默认方向是垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(adapter);
        holder.mRecyclerView.setHasFixedSize(true);

        holder.mRecyclerView.addOnPageChangedListener((int i, int i1) ->
                ((StartReadActivity) mContext).onHorizontalItemSelected(i1)
        );
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewPager mRecyclerView;

        public SimpleViewHolder(View view) {
            super(view);
            mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.rv_read);
        }
    }
}
