package com.cypoem.idea.module.wrapper;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.module.bean.Meizi;

import java.util.List;

/**
 * Created by zhpan on 2017/7/2.
 */

public class HomePageWrapper extends BasicResponse {
    private List<HomePageBean.ResultBean> result;

    public List<HomePageBean.ResultBean> getResult() {
        return result;
    }

    public void setResult(List<HomePageBean.ResultBean> result) {
        this.result = result;
    }
}
