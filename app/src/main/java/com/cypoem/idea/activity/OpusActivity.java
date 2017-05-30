package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.OpusAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import butterknife.BindView;

public class OpusActivity extends BaseActivity {
    @BindView(R.id.rv_opus)
    RecyclerViewPager mRecyclerView;
    @BindView(R.id.ll_tab)
    LinearLayout mLlTab;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_opus;
    }

    @Override
    protected void init() {
        initData();
        setRecyclerView();
    }

    private void initData() {

    }

    private void setRecyclerView() {
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(new OpusAdapter(this,OpusAdapter.OPUS));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnPageChangedListener((int i, int i1)-> showToast("vertical position="+i1));

        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int i, int i1) {
                startAnim();
            }
        });

    }

    public void startAnim(){
        Animation animation = AnimationUtils
                .loadAnimation(OpusActivity.this, R.anim.draw_down);
        mLlTab.startAnimation(animation);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OpusActivity.class);
        context.startActivity(intent);
    }
}
