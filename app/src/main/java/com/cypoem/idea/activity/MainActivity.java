package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtn2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.btn).setOnClickListener(v->getData());
        findViewById(R.id.btn2).setOnClickListener((v)->show());
    }

    private void show() {
        showTwoButtonDialog("是否退出Dialog？","确定", "取消",
                (v)->dismissDialog(), (v)-> dismissDialog());
    }

    private void getData() {
        IdeaApi.getApiService()
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size()-content.size()+2; i++) {
                            Toast.makeText(MainActivity.this, "Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn2:

                break;
        }
    }
}
