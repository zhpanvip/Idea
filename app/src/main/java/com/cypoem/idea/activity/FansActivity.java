package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.FansAdapter;
import com.cypoem.idea.module.bean.FansBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FansActivity extends BaseActivity {

    @BindView(R.id.lv_fans)
    ListView lvFans;
    private FansAdapter mAdapter;
    private List<FansBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        mList=new ArrayList<>();
        FansBean fansBean=new FansBean();
        fansBean.setCreate(15);
        fansBean.setHeadUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=304866327,2141533711&fm=11&gp=0.jpg");
        fansBean.setJoin(55);
        fansBean.setName("韩三少");
        fansBean.setSing("一直被模仿，从未被超越");
        fansBean.setPublish(10);
        mList.add(fansBean);
        mList.add(fansBean);
        mList.add(fansBean);
        mList.add(fansBean);
        mAdapter=new FansAdapter(this,R.layout.item_fans);
        mAdapter.setList(mList);
        lvFans.setAdapter(mAdapter);
    }

    public static void start(Context context){
        Intent intent=new Intent(context,FansActivity.class);
        context.startActivity(intent);
    }
}
