package com.cypoem.idea.module.wrapper;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;

import java.util.List;

/**
 * Created by zhpan on 2017/7/8.
 */

public class ArticleWrapper extends BasicResponse {
    private List<ArticleBean.ResultBean> results;

    public List<ArticleBean.ResultBean> getResults() {
        return results;
    }

    public void setResult(List<ArticleBean.ResultBean> results) {
        this.results = results;
    }
}
