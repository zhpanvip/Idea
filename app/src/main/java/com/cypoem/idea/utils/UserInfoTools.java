package com.cypoem.idea.utils;

import android.content.Context;

import com.cypoem.idea.module.bean.UserInfoBean;

/**
 * Created by edianzu on 2017/5/25.
 */

public class UserInfoTools {
    private static UserInfoBean mUserInfoBean;

    public static void init(Context context) {
        mUserInfoBean = getUserInfoBean(context);
        if (mUserInfoBean == null) {
            mUserInfoBean = new UserInfoBean();
            mUserInfoBean.setUserAccount("");
            mUserInfoBean.setPassword("");
            mUserInfoBean.isLogin = false;
            //userInfoBean.isHelpered = false;
            mUserInfoBean.setTokenId("");
        }
        setUserInfoBean(context, mUserInfoBean);
    }

    /**
     * 获取本地个人信息
     */
    private static void setUserInfoBean(Context context, UserInfoBean userInfoBean) {
        mUserInfoBean = userInfoBean;

        SharedPreferencesHelper.saveObject(context, mUserInfoBean);
    }


    public static UserInfoBean getUserInfoBean(Context context) {
        if (mUserInfoBean == null) {
            mUserInfoBean = SharedPreferencesHelper.getObject(context.getApplicationContext(), UserInfoBean.class);
        }
        if (mUserInfoBean == null) {
            mUserInfoBean = new UserInfoBean();
        }
        return mUserInfoBean;
    }

    /**
     * 设置用户手机IMEI
     */
    public static void setImei(Context context, String imei) {
        mUserInfoBean.setImei(imei);
        setUserInfoBean(context, mUserInfoBean);
    }

    public static String getImei(Context context){
        String imei;
        if(mUserInfoBean!=null){
            imei=mUserInfoBean.getImei();
        }else {
            imei=getUserInfoBean(context).getImei();
        }

        return imei;
    }

    public static void setBrand(Context context, String brand) {
        mUserInfoBean.setBrand(brand);
        setUserInfoBean(context, mUserInfoBean);
    }

    public static String getBrand(Context context) {
        String brand;
        if (mUserInfoBean != null) {
            brand = mUserInfoBean.getBrand();
        } else {
            brand = getUserInfoBean(context).getBrand();
        }
        return brand;
    }

    public static void setOsVersion(Context context, String osVersion) {
        mUserInfoBean.setOsVersion(osVersion);
        setUserInfoBean(context, mUserInfoBean);
    }

    public static String getOsVersion(Context context) {
        String osVersion;
        if (mUserInfoBean != null) {
            osVersion = mUserInfoBean.getOsVersion();
        } else {
            osVersion = getUserInfoBean(context).getOsVersion();
        }
        return osVersion;
    }

    public static void setToken(Context context, String token) {
        mUserInfoBean.setTokenId(token);
        setUserInfoBean(context, mUserInfoBean);
    }

    public static String getToken(Context context) {
        String token;
        if (mUserInfoBean != null) {
            token = mUserInfoBean.getTokenId();
        } else {
            token = getUserInfoBean(context).getTokenId();
        }
        return token;
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        mUserInfoBean.setLogin(isLogin);
        setUserInfoBean(context, mUserInfoBean);
    }

    public static boolean getIsLogin(Context context) {
        boolean isLogin;
        if (mUserInfoBean != null) {
            isLogin = mUserInfoBean.isLogin();
        } else {
            isLogin = getUserInfoBean(context).isLogin();
        }
        return isLogin;
    }


}
