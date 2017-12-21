package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleListBean;
import com.cypoem.idea.module.bean.WriteBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewestArticleActivity extends BaseActivity {


    @BindView(R.id.rv_story)
    RecyclerView mRecyclerView;
    private int type = 1;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newest_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setRefreshLayout(true);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getData();
    }

    private void getData() {
        IdeaApi.getApiService()
                .findStory(UserInfoTools.getUserId(this), type, page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArticleListBean>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<ArticleListBean> response) {
                        ArticleListBean result = response.getResult();
                        List<WriteBean> articles = result.getWrites();
                        requestSuccess(articles);
                    }
                });
    }

    private void requestSuccess(List<WriteBean> articles) {
        ArticleAdapter adapter = new ArticleAdapter(this);
        adapter.fillList(articles);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewestArticleActivity.class);
        context.startActivity(intent);
    }
}
