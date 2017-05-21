package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.module.bean.CollectBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.lv_collect)
    ListView mListView;
    private List<CollectBean> mList;
    private CollectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        mList=new ArrayList<>();
        CollectBean collectBean=new CollectBean();
        collectBean.setTime("2017-05-20");
        collectBean.setPicUrl("http://pic17.nipic.com/20111020/6337790_120550160000_2.jpg");
        collectBean.setArticlesNum(15);
        collectBean.setAuther("含箫剑");
        collectBean.setReadNum(565);
        collectBean.setPraiseNum(122);
        collectBean.setTitle("一只单身汪的日常");
        collectBean.setSubTitle("汪汪汪");
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mList.add(collectBean);
        mAdapter=new CollectAdapter(this,R.layout.item_collect);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
    }
    public static void start(Context context){
        Intent intent=new Intent(context,CollectActivity.class);
        context.startActivity(intent);
    }
}
