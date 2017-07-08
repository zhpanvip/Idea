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
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.wrapper.ArticleWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String writeId;
    private String sectionId;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_read;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        sectionId = intent.getStringExtra("sectionId");

        List<ArticleBean> list = new ArrayList<>();
        mAdapter = new StartReadAdapter(this);
        mAdapter.setList(list);

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

        mRecyclerView.addOnPageChangedListener((int i, int i1) -> {
            showToast("vertical position=" + i1);
            startAnim();
        });

        //getData(true, page);
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

    public static void start(Context context,String sectionId) {
        Intent intent = new Intent(context, StartReadActivity.class);
        intent.putExtra("sectionId",sectionId);
        context.startActivity(intent);
    }

    private void getData(boolean showLoading, int page) {
        IdeaApi.getApiService()
                .getArticle(UserInfoTools.getUserId(this), page, Constants.NUM, sectionId, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ArticleWrapper>(this, showLoading) {
                    @Override
                    public void onSuccess(ArticleWrapper response) {
                        List<ArticleBean.ResultBean> results = response.getResults();
                        //mAdapter.getList().addAll(results);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}
