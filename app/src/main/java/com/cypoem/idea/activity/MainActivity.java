package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.ListData;
import com.cypoem.idea.module.wrapper.DataWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        IdeaApi.getApiService()
                .getData("json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<DataWrapper>(this) {
                    @Override
                    public void onSuccess(DataWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<ListData.ListBean> content = response.getList();
                        for (int i = 0; i < content.size(); i++) {
                            Toast.makeText(MainActivity.this, "第" + (i + 1) + "条数据Password:" + content.get(i).getPsw(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(DataWrapper response) {

                    }
                });
    }

    private void initView() {
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
