package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.util.SparseIntArray;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.module.bean.ArticleBean;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/29.
 */

public class StartReadAdapter extends RecyclerView.Adapter<StartReadAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 100;

    private final Context mContext;
    private List<List<ArticleBean>> mItems;
    private SparseIntArray mIndexMap;

    public android.util.SparseIntArray getmIndexMap() {
        return mIndexMap;
    }

    public void setmIndexMap(android.util.SparseIntArray mIndexMap) {
        this.mIndexMap = mIndexMap;
    }

    public List<List<ArticleBean>> getList() {
        return mItems;
    }

    public void setList(List<List<ArticleBean>> list) {
        mItems = list;
    }

    public StartReadAdapter(Context context) {
        mContext = context;
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
        List<ArticleBean> articleList = mItems.get(position);
        adapter.setList(articleList);
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置显示布局的方向，默认方向是垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(adapter);
        holder.mRecyclerView.setHasFixedSize(true);
       // holder.mRecyclerView.scrollToPosition(mIndexMap.get(position));
        holder.mRecyclerView.clearOnPageChangedListeners();
        holder.mRecyclerView.addOnPageChangedListener((int i, int i1) -> {
                    LogUtils.e(position + "---------------" + i);
                    ((StartReadActivity) mContext).onHorizontalItemSelected(position, i1);
                }
        );
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private RecyclerViewPager mRecyclerView;

        private SimpleViewHolder(View view) {
            super(view);
            mRecyclerView = (RecyclerViewPager) view.findViewById(R.id.rv_read);
        }
    }
}
