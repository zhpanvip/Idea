package com.cypoem.idea.fragment;

import android.widget.Toast;

import com.airong.core.BaseRxFragment;
import com.cypoem.idea.R;
import com.cypoem.idea.activity.MainActivity;
import com.cypoem.idea.module.bean.Meizi;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/4/21.
 */

public class FindFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init() {
        IdeaApi.getApiService()
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(getContext(), "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < content.size()-content.size()+2; i++) {
                            Toast.makeText(getContext(), "Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
