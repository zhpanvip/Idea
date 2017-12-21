package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/12/20.
 */

public class CircleResponse {

    private int count;
    private List<CircleBean> circles;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CircleBean> getCircles() {
        return circles;
    }

    public void setCircles(List<CircleBean> circles) {
        this.circles = circles;
    }


}
