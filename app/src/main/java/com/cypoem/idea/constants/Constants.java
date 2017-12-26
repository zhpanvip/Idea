package com.cypoem.idea.constants;

/**
 * Created by zhpan on 2017/4/19.
 */

public interface Constants {
    int NUM = 10;   //  每页加载数据的条数

    int REQUEST_SELECT_PICTURE = 0x01;
    String TAG = "CropImage";
    /**
     * 关于我们
     */
    String ABOUT_US = "http://cypoem.com:8080/cys/cypoem-regulations.html?type=0";
    String ABOUT_US_NIGHT = "file:///android_asset/www/about_us_night.html";
    /**
     * 协议相关
     */
    String PROTOCOL = "http://cypoem.com:8080/cys/cypoem-regulations.html?type=1";
    String PROTOCOL_NIGHT = "file:///android_asset/www/protocol_night.html";

    /**
     * 登录条款
     */

    String Login_ITEM="http://cypoem.com:8080/cys/cypoem-regulations.html?type=4";
    /**
     * 平台常见问题
     */
    String PLATFORM_QUESTION="http://cypoem.com:8080/cys/cypoem-regulations.html?type=3";

    /**
     * 钱包常见问我难题
     */
    String WALLET_QUESTIONS="http://cypoem.com:8080/cys/cypoem-regulations.html?type=5";

    /**
     * 线下读书会说明
     */
    String READ_MEETING_PRODUCE="http://cypoem.com:8080/cys/cypoem-regulations.html?type=6";

    /**
     * 读书会
     */
    String READ_MEETING="http://www.cypoem.com:8080/cys/readparty.html";


    int MY_START_OPUS = 1;  //  我发起的
    int MY_OWN_OPUS = 2;    //  我原创的
    int MY_JOIN_OPUS = 3;   //  我参与的
    int MY_DRAFT=4;     //  我的草稿

    int FOLLOWS=2;  //  粉丝
    int FOCUS=1;    //  关注
    int HOT_AUTHOR=3;// 热门作者

    int HOME = 0;   //  主页

    //  上传头像
    String HEAD_PIC="1";
    String HEAD_COVER="0";
}
