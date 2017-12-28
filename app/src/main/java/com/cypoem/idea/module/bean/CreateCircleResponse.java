package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/12/10.
 */

public class CreateCircleResponse {

    /**
     * circle : {"category":"暗恋","circleId":12,"date":"2017-10-23 10:38","icon":"/cys/upload/img/circle/uid/20171023103801328寻情.jpg","introduction":"喜欢一个人","name":"喜欢你没道理","privateStoryCount":0,"publicStoryCount":0,"shareCount":0,"status":"1","stick":0,"type":"2","user":{"icon":"/cys/upload/img/user/uid/icon/20170912174506318sss.png","pen_name":"棉花草","user_id":2017060025},"userCount":0}
     */

    private CircleBean circle;

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
    }

}
