package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.adapter.CommonAdapter;
import com.airong.core.adapter.CommonViewHolder;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.StartReadBean;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/5/29.
 */

public class StartReadAdapter extends RecyclerView.Adapter<StartReadAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 100;

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;
    public StartReadAdapter(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, DEFAULT_ITEM_COUNT);
    }

    public StartReadAdapter(Context context, RecyclerView recyclerView, int itemCount) {
        mContext = context;
        mItems = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            addItem(i);
        }

        mRecyclerView = recyclerView;
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
        //holder.title.setText(mItems.get(position).toString());
        StartReadHorizontalAdapter adapter=new StartReadHorizontalAdapter(mContext);
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置显示布局的方向，默认方向是垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        holder.mRecylerView.setLayoutManager(linearLayoutManager);
        holder.mRecylerView.setAdapter(adapter);
        holder.mRecylerView.setHasFixedSize(true);

        holder.mRecylerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                Toast.makeText(mContext, "position="+i1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initViewPager(RecyclerViewPager mRecyclerViewPager) {


        LinearLayoutManager layout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerViewPager.setTriggerOffset(0.15f);
        mRecyclerViewPager.setFlingFactor(0.25f);
        mRecyclerViewPager.setLayoutManager(layout);
        mRecyclerViewPager.setAdapter(new StartReadAdapter(mContext, mRecyclerViewPager));
        mRecyclerViewPager.setHasFixedSize(true);
        mRecyclerViewPager.setLongClickable(true);

        mRecyclerViewPager.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerViewPager.getChildCount();
                int width = mRecyclerViewPager.getChildAt(0).getWidth();
                int padding = (mRecyclerViewPager.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerViewPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerViewPager.getChildCount() < 3) {
                    if (mRecyclerViewPager.getChildAt(1) != null) {
                        View v1 = mRecyclerViewPager.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerViewPager.getChildAt(0) != null) {
                        View v0 = mRecyclerViewPager.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerViewPager.getChildAt(2) != null) {
                        View v2 = mRecyclerViewPager.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


   /* public StartReadAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext=context;

    }

    @Override
    public void convert(CommonViewHolder holder, StartReadBean startReadBean, List<StartReadBean> list) {
        RecyclerView recyclerView = holder.getView(R.id.rv_read);
        StartReadHorizontalAdapter adapter=new StartReadHorizontalAdapter(mContext);
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置显示布局的方向，默认方向是垂直
       linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }*/

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
       // public final TextView title;
        public RecyclerViewPager mRecylerView;

        public SimpleViewHolder(View view) {
            super(view);
            mRecylerView= (RecyclerViewPager) view.findViewById(R.id.rv_read);
           // title = (TextView) view.findViewById(id.title);
        }
    }
}
