package com.cypoem.idea.constants;

/**
 * Created by zhpan on 2017/4/19.
 */

public interface Constants {
    int NUM = 10;   //  每页加载数据的条数

    int REQUEST_SELECT_PICTURE = 0x01;
    String TAG = "CropImage";
    String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
    String ABOUT_US = "file:///android_asset/www/about_us.html";
    String ABOUT_US_NIGHT = "file:///android_asset/www/about_us_night.html";
    String PROTOCOL = "file:///android_asset/www/protocol.html";
    String PROTOCOL_NIGHT = "file:///android_asset/www/protocol_night.html";

    /**
     * 读书会
     */
    String READ_MEETING="http://www.cypoem.com:8080/cys/readparty.html";


    int MY_START_OPUS = 1;  //  我发起的
    int MY_OWN_OPUS = 2;    //  我原创的
    int MY_JOIN_OPUS = 3;   //  我参与的
    int MY_DRAFT=4;     //  我的草稿

    int FOLLOWS=0;  //  粉丝
    int FOCUS=1;    //  关注

    int HOME = 0;   //  主页

    //  上传头像
    String HEAD_PIC="1";
    String HEAD_COVER="0";
}
