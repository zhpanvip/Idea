package com.cypoem.idea.module.wrapper;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ListData;

import java.util.List;


/**
 * Created by zhpan on 2017/4/18.
 */

public class DataWrapper extends BasicResponse {
    List<ListData.ListBean> list;

    public List<ListData.ListBean> getList() {
        return list;
    }

    public void setList(List<ListData.ListBean> list) {
        this.list = list;
    }
}
