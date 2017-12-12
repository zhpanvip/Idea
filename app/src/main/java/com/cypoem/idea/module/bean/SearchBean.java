package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/12/12.
 */

public class SearchBean {

    private List<CircleBean> circles;
    private List<WriteBean> writes;
    private List<UserBean> users;

    public List<CircleBean> getCircles() {
        return circles;
    }

    public void setCircles(List<CircleBean> circles) {
        this.circles = circles;
    }

    public List<WriteBean> getWrites() {
        return writes;
    }

    public void setWrites(List<WriteBean> writes) {
        this.writes = writes;
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

}
