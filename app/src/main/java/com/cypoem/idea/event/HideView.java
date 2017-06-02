package com.cypoem.idea.event;

/**
 * Created by zhpan on 2017/6/2.
 * Description: EventBus隐藏MeFragment中footer的事件类
 */

public class HideView {
    public   boolean isHide;
    public HideView(boolean isHide) {
        this.isHide = isHide;
    }
}
