package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.StartReadAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import butterknife.BindView;

public class StartReadActivity extends BaseActivity {

    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_override)
    TextView tvOverride;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.rv_read)
    RecyclerViewPager mRecyclerView;

    private StartReadAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_read;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        /*mAdapter = new StartReadAdapter(this, R.layout.item_read);
        List<StartReadBean> list = new ArrayList<>();
        list.add(new StartReadBean());
        list.add(new StartReadBean());
        list.add(new StartReadBean());
        list.add(new StartReadBean());
        mAdapter.setList(list);
        mListView.setAdapter(mAdapter);*/

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(new StartReadAdapter(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

        mRecyclerView.addOnPageChangedListener((int i, int i1) -> {
            showToast("vertical position=" + i1);
            startAnim();
        });

       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getTop() <= padding) {
                        if (v.getTop() >= padding - v.getHeight()) {
                            rate = (padding - v.getTop()) * 1f / v.getHeight();
                        } else {
                            rate = 1;
                        }
                        v.setScaleX(1 - rate * 0.1f);
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getHeight()-padding 的过程中，由大到小
                        if (v.getTop() <= recyclerView.getHeight() - padding) {
                            rate = (recyclerView.getHeight() - padding - v.getTop()) * 1f / v.getHeight();
                        }
                        v.setScaleX(0.9f + rate * 0.1f);
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });*/

        /*mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });*/
    }

    public void onHorizontalItemSelected(int position) {
        showToast("horizontal position=" + position);
        startAnim();
    }

    public void startAnim() {
        Animation animation = AnimationUtils
                .loadAnimation(StartReadActivity.this, R.anim.draw_down);
        llTab.startAnimation(animation);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, StartReadActivity.class);
        context.startActivity(intent);
    }


}
