package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.StartReadAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StartReadActivity extends BaseActivity {

    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.rv_read)
    RecyclerViewPager mRecyclerView;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.ll_value)
    LinearLayout llValue;
    @BindView(R.id.tv_rewrite)
    TextView tvRewrite;
    @BindView(R.id.ll_rewrite)
    LinearLayout llRewrite;
    @BindView(R.id.ll_continue)
    LinearLayout llContinue;

    private StartReadAdapter mAdapter;
    private int page = 1;
    private String authorId;
    private String writeId;

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
        authorId = intent.getStringExtra("authorId");
        writeId = intent.getStringExtra("writeId");

        List<List<ArticleBean>> list = new ArrayList<>();
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

        getData(true, page);
    }

    public void onHorizontalItemSelected(int position, int id) {
        showToast("horizontal position=" + position);
        startAnim();
        setArticleData(position, id);
    }

    private void setArticleData(int position, int id) {
        List<ArticleBean> articleBeen = mAdapter.getList().get(position);
        ArticleBean articleBean = articleBeen.get(id);
        tvComment.setText(String.valueOf(articleBean.getComment_count()));
        tvContinue.setText(String.valueOf(articleBean.getRead_count()));
        tvLike.setText(String.valueOf(articleBean.getLike_count()));
        tvRewrite.setText(String.valueOf(articleBean.getEnjoy_count()));
        tvValue.setText(String.valueOf(articleBean.getEnjoy_count()));
    }

    public void startAnim() {
        Animation animation = AnimationUtils
                .loadAnimation(StartReadActivity.this, R.anim.draw_down);
        llTab.startAnimation(animation);
    }

    public static void start(Context context, String writeId, String authorId) {
        Intent intent = new Intent(context, StartReadActivity.class);
        intent.putExtra("writeId", writeId);
        intent.putExtra("authorId", authorId);
        context.startActivity(intent);
    }

    private void getData(boolean showLoading, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", "0");
        map.put("user_id", UserInfoTools.getUserId(this));
        map.put("page", page + "");
        map.put("rows", "10");
        map.put("write_id", writeId);
        map.put("write_author_id", authorId);
        IdeaApi.getApiService()
                .getArticle(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<ArticleBean>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<ArticleBean>> response) {
                        List<ArticleBean> results = response.getResult();
                        mAdapter.getList().add(results);
                        mAdapter.notifyDataSetChanged();
                        if(results.size()>0){
                            setArticleData(0, 0);
                        }
                    }
                });
    }

    @OnClick({R.id.ll_comment, R.id.ll_like, R.id.ll_value, R.id.ll_rewrite, R.id.ll_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                break;
            case R.id.ll_like:
                break;
            case R.id.ll_value:
                break;
            case R.id.ll_rewrite:
                break;
            case R.id.ll_continue:
                break;
        }
    }
}
