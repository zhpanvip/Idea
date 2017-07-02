package com.cypoem.idea.module.wrapper;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ChaptersBean;

import java.util.List;

/**
 * Created by zhpan on 2017/7/2.
 */

public class ChaptersWrapper extends BasicResponse {
    private List<ChaptersBean> result;

    public List<ChaptersBean> getResult() {
        return result;
    }

    public void setResult(List<ChaptersBean> result) {
        this.result = result;
    }
}
