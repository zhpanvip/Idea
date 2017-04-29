package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.airong.core.view.PtrClassicListFooter;
import com.airong.core.view.PtrClassicListHeader;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.store_house_ptr_frame)
     PtrFrameLayout mPtrFrame;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void init() {
        initPtr();
    }

    private void initPtr() {
        mPtrFrame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        PtrClassicListHeader header = new PtrClassicListHeader(this);
        header.setLastUpdateTimeRelateObject(this);
        PtrClassicListFooter footer = new PtrClassicListFooter(this);
        footer.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setFooterView(footer);
        mPtrFrame.addPtrUIHandler(footer);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed((() -> getData(false)), 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed((() -> getData(false)), 1000);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, content, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        mPtrFrame.postDelayed((() -> mPtrFrame.autoRefresh()), 1000);
    }

    private void getData(boolean showLoading) {
        //  Retrofit请求数据
        IdeaApi.getApiService()
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this, showLoading) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size() - content.size() + 2; i++) {
                            Toast.makeText(MainActivity.this, "Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    //  显示弹窗
    private void show() {
        showTwoButtonDialog("是否退出Dialog？", "确定", "取消",
                (v) -> dismissDialog(), (v) -> dismissDialog());
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @OnClick({R.id.btn, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                getData(true);
                break;
            case R.id.btn2:
                show();
                break;
        }
    }
}
