package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.EverydayAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.EverydayReBackBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.ArrayList;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EverydayLookBackActivity extends BaseActivity {

    @BindView(R.id.rv_opus)
    RecyclerViewPager mViewPager;
    private EverydayAdapter adapter;
    private int page=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_everyday_reback;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter=new EverydayAdapter(this);
        adapter.setList(new ArrayList<>());
        // 创建线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // 设置显示布局的方向，默认方向是垂直
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        mViewPager.setLayoutManager(linearLayoutManager);
        mViewPager.setAdapter(adapter);
        mViewPager.setHasFixedSize(true);

        mViewPager.addOnPageChangedListener((int i, int i1) ->{

        } );

        getData(true,page);
    }

    private void getData(boolean showLoading,int page) {
        IdeaApi.getApiService()
                .lookBack(page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<EverydayReBackBean>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<EverydayReBackBean> response) {

                        adapter.getList().addAll(response.getResult().getEverySay());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public static void start(Context context){
        Intent intent=new Intent(context,EverydayLookBackActivity.class);
        context.startActivity(intent);
    }

}
