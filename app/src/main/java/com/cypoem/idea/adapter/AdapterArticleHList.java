package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.ArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/11/11.
 */

public class AdapterArticleHList extends BaseAdapter<ArticleBean, AdapterArticleHList.ArticleHHolder> {


    public AdapterArticleHList(Context context) {
        super(context);
        List<ArticleBean> list = new ArrayList<>();
        list.add(new ArticleBean());
        list.add(new ArticleBean());
        list.add(new ArticleBean());
        fillList(list);
    }

    @Override
    public ArticleHHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ArticleHHolder(parent, R.layout.item_article_hori_list);
    }

    @Override
    public void bindCustomViewHolder(ArticleHHolder holder, int position) {

    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class ArticleHHolder extends BaseHolder {

        public ArticleHHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
        }
    }

}
