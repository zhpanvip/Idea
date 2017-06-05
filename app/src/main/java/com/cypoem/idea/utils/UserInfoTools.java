package com.cypoem.idea.utils;

import android.content.Context;
import com.cypoem.idea.module.bean.UserInfoBean;

/**
 * Created by edianzu on 2017/5/25.
 */

public class UserInfoTools {
    private static UserInfoBean sUserInfoBean;

    public static void init(Context context) {
        sUserInfoBean = getUserInfoBean(context);
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();
            sUserInfoBean.setUserAccount("");
            sUserInfoBean.setPassword("");
            sUserInfoBean.setLogin(false);
            //userInfoBean.isHelpered = false;
            sUserInfoBean.setTokenId("");
        }
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取本地个人信息
     */
    private static void setUserInfoBean(Context context, UserInfoBean userInfoBean) {
        sUserInfoBean = userInfoBean;
        SharedPreferencesHelper.saveObject(context, sUserInfoBean);
    }


    public static UserInfoBean getUserInfoBean(Context context) {
        if (sUserInfoBean == null) {
            sUserInfoBean = SharedPreferencesHelper.getObject(context.getApplicationContext(), UserInfoBean.class);
        }
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();
        }
        return sUserInfoBean;
    }

    /**
     * 设置用户手机IMEI
     */
    public static void setImei(Context context, String imei) {
        sUserInfoBean.setImei(imei);
        setUserInfoBean(context, sUserInfoBean);
    }

    public static String getImei(Context context){
        String imei;
        if(sUserInfoBean !=null){
            imei= sUserInfoBean.getImei();
        }else {
            imei=getUserInfoBean(context).getImei();
        }

        return imei;
    }

    public static void setBrand(Context context, String brand) {
        sUserInfoBean.setBrand(brand);
        setUserInfoBean(context, sUserInfoBean);
    }

    public static String getBrand(Context context) {
        String brand;
        if (sUserInfoBean != null) {
            brand = sUserInfoBean.getBrand();
        } else {
            brand = getUserInfoBean(context).getBrand();
        }
        return brand;
    }

    public static void setOsVersion(Context context, String osVersion) {
        sUserInfoBean.setOsVersion(osVersion);
        setUserInfoBean(context, sUserInfoBean);
    }

    public static String getOsVersion(Context context) {
        String osVersion;
        if (sUserInfoBean != null) {
            osVersion = sUserInfoBean.getOsVersion();
        } else {
            osVersion = getUserInfoBean(context).getOsVersion();
        }
        return osVersion;
    }

    public static void setToken(Context context, String token) {
        sUserInfoBean.setTokenId(token);
        setUserInfoBean(context, sUserInfoBean);
    }


    public static String getToken(Context context) {
        String token;
        if (sUserInfoBean != null) {
            token = sUserInfoBean.getTokenId();
        } else {
            token = getUserInfoBean(context).getTokenId();
        }
        return token;
    }

    /**
     * @param isLogin 是否登录
     */
    public static void setIsLogin(Context context, boolean isLogin) {
        sUserInfoBean.setLogin(isLogin);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * @return 是否登录
     */
    public static boolean getIsLogin(Context context) {
        boolean isLogin;
        if (sUserInfoBean != null) {
            isLogin = sUserInfoBean.isLogin();
        } else {
            isLogin = getUserInfoBean(context).isLogin();
        }
        return isLogin;
    }

    /**
     *
     * @param isNightMode 是否开启夜间模式
     */
    public static void setNightMode(Context context,boolean isNightMode){
        sUserInfoBean.setNightMode(isNightMode);
        setUserInfoBean(context,sUserInfoBean);
    }

    /**
     * @return 是否是夜间模式
     */
    public static boolean isNightMode(Context context){
        boolean isNightMode;
        if (sUserInfoBean != null) {
            isNightMode = sUserInfoBean.isNightMode();
        } else {
            isNightMode = getUserInfoBean(context).isNightMode();
        }
        return isNightMode;
    }


}
