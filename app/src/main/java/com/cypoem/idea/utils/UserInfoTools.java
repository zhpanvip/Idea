package com.cypoem.idea.utils;

import android.content.Context;

import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.UserInfoBean;

/**
 * Created by zhapan on 2017/5/25.
 */

public class UserInfoTools {
    private static UserInfoBean sUserInfoBean;

    public static void init(Context context) {
        sUserInfoBean = getUserInfoBean(context);
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();

            //userInfoBean.isHelpered = false;
            sUserInfoBean.setTokenId("");
        }
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 设置本地个人信息
     */
    private static void setUserInfoBean(Context context, UserInfoBean userInfoBean) {
        sUserInfoBean = userInfoBean;
        SharedPreferencesHelper.saveObject(context, sUserInfoBean);
    }

    public static void setChangeNightMode(Context context, boolean isChanged) {
        SharedPreferencesHelper.put(context, "isChangeNightMode", isChanged);
    }

    public static boolean isChangeNightMode(Context context) {
        boolean isChangeNightMode = (boolean) SharedPreferencesHelper.get(context, "isChangeNightMode", false);
        return isChangeNightMode;
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

    public static String getImei(Context context) {
        String imei;
        if (sUserInfoBean != null) {
            imei = sUserInfoBean.getImei();
        } else {
            imei = getUserInfoBean(context).getImei();
        }

        return imei;
    }

    /**
     * 保存手机号
     *
     * @param context
     * @param phone
     */
    public static void setPhone(Context context, String phone) {
        UserBean user = sUserInfoBean.getUser();
        user.setPhone(phone);
        sUserInfoBean.setUser(user);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户手机号
     *
     * @param context
     * @return 用户手机号
     */
    public static String getPhone(Context context) {
        String phone;
        if (sUserInfoBean != null) {
            phone = sUserInfoBean.getUser().getPhone();
        } else {
            phone = getUserInfoBean(context).getUser().getPhone();
        }
        return phone;
    }

    /**
     * 保存密码
     *
     * @param context  context
     * @param password 密码
     */
    public static void setPassword(Context context, String password) {
        sUserInfoBean.setPassword(password);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户密码
     *
     * @param context context
     * @return
     */
    public static String getPassword(Context context) {
        String password;
        if (sUserInfoBean != null) {
            password = sUserInfoBean.getPassword();
        } else {
            password = getUserInfoBean(context).getPassword();
        }
        return password;
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

    public static void setUser(Context context, UserBean user) {
        sUserInfoBean.setUser(user);
        setUserInfoBean(context, sUserInfoBean);
    }

    public static UserBean getUser(Context context) {
        UserBean user;
        if (sUserInfoBean != null) {
            user = sUserInfoBean.getUser();
        } else {
            user = getUserInfoBean(context).getUser();
        }
        return user;
    }

    /**
     * 获取用户id
     *
     * @param context
     * @return
     */
    public static String getUserId(Context context) {
        String userId;
        if (sUserInfoBean != null) {
            userId = sUserInfoBean.getUser().getUserId();
        } else {
            userId = getUserInfoBean(context).getUser().getUserId();
        }
        if (null == userId) {
            userId = "";
        }
        return userId;
    }

    /**
     * 保存用户头像
     *
     * @param context
     * @param url
     */
    public static void setHeadPic(Context context, String url) {
        sUserInfoBean.getUser().setIcon(url);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 保存用户头像
     *
     * @param context
     * @return
     */
    public static String getHeadPic(Context context) {
        String url;
        if (sUserInfoBean != null) {
            url = sUserInfoBean.getUser().getIcon();
        } else {
            url = getUserInfoBean(context).getUser().getIcon();
        }
        if (null == url) {
            url = "";
        }
        return url;
    }

    public static String getCover(Context context) {
        String url;
        if (sUserInfoBean != null) {
            url = sUserInfoBean.getUser().getCover_photo();
        } else {
            url = getUserInfoBean(context).getUser().getCover_photo();
        }
        if (null == url) {
            url = "";
        }
        return url;
    }
    /**
     * 保存用户头像
     *
     * @param context
     * @param url
     */
    public static void setCover(Context context, String url) {
        sUserInfoBean.getUser().setCover_photo(url);
        setUserInfoBean(context, sUserInfoBean);
    }



    /**
     * 保存用户笔名
     *
     * @param context
     * @param penName
     */
    public static void setPenName(Context context, String penName) {
        sUserInfoBean.getUser().setPen_name(penName);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户笔名
     *
     * @param context
     * @return
     */
    public static String getPenName(Context context) {
        String penName;
        if (sUserInfoBean != null) {
            penName = sUserInfoBean.getUser().getPen_name();
        } else {
            penName = getUserInfoBean(context).getUser().getPen_name();
        }
        if (null == penName) {
            penName = "";
        }
        return penName;
    }

    /**
     * 保存用户性别
     *
     * @param context
     * @param sex
     */
    public static void setSex(Context context, String sex) {
        sUserInfoBean.getUser().setSex(sex);
        setUserInfoBean(context, sUserInfoBean);
    }


    /**
     * 获取用户性别
     *
     * @param context
     * @return
     */
    public static String getSex(Context context) {
        String sex;
        if (sUserInfoBean != null) {
            sex = sUserInfoBean.getUser().getSex();
        } else {
            sex = getUserInfoBean(context).getUser().getSex();
        }
        if (null == sex) {
            sex = "0.5";
        }
        return sex;
    }

    /**
     * 保存用户生日
     *
     * @param context
     * @param birthday
     */
    public static void setBirthday(Context context, String birthday) {
        sUserInfoBean.getUser().setBirthday(birthday);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户生日
     *
     * @param context
     * @return
     */
    public static String getBirthday(Context context) {
        String birthday;
        if (sUserInfoBean != null) {
            birthday = sUserInfoBean.getUser().getBirthday();
        } else {
            birthday = getUserInfoBean(context).getUser().getBirthday();
        }
        if (null == birthday) {
            birthday = "";
        }
        return birthday;
    }


    /**
     * 保存用户地址
     *
     * @param context
     * @param address
     */
    public static void setAddress(Context context, String address) {
        sUserInfoBean.getUser().setAddress(address);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户地址
     *
     * @param context
     * @return
     */
    public static String getAddress(Context context) {
        String address;
        if (sUserInfoBean != null) {
            address = sUserInfoBean.getUser().getAddress();
        } else {
            address = getUserInfoBean(context).getUser().getAddress();
        }
        if (null == address) {
            address = "";
        }
        return address;
    }

    /**
     * 保存用户格言
     *
     * @param context
     * @param sign
     */
    public static void setSign(Context context, String sign) {
        sUserInfoBean.getUser().setDictum(sign);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户格言
     *
     * @param context
     * @return
     */
    public static String getSign(Context context) {
        String sign;
        if (sUserInfoBean != null) {
            sign = sUserInfoBean.getUser().getDictum();
        } else {
            sign = getUserInfoBean(context).getUser().getDictum();
        }
        if (null == sign) {
            sign = "";
        }
        return sign;
    }


    /**
     * 保存用户简介
     *
     * @param context
     * @param introduce
     */
    public static void setAudthorBrief(Context context, String introduce) {
        sUserInfoBean.getUser().setIntroduction(introduce);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户简介
     *
     * @param context
     * @return
     */
    public static String getAudthorBrief(Context context) {
        String introduce;
        if (sUserInfoBean != null) {
            introduce = sUserInfoBean.getUser().getIntroduction();
        } else {
            introduce = getUserInfoBean(context).getUser().getIntroduction();
        }
        if (null == introduce) {
            introduce = "";
        }
        return introduce;
    }

    /**
     * 保存用户相片
     *
     * @param context
     * @param coverPic
     */
    public static void setCoverPic(Context context, String coverPic) {
        sUserInfoBean.getUser().setCover_photo(coverPic);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * 获取用户相片
     *
     * @param context
     * @return
     */
    public static String getCoverPic(Context context) {
        String coverPic;
        if (sUserInfoBean != null) {
            coverPic = sUserInfoBean.getUser().getCover_photo();
        } else {
            coverPic = getUserInfoBean(context).getUser().getCover_photo();
        }
        if (null == coverPic) {
            coverPic = "";
        }
        return coverPic;
    }


    /**
     * @param isNightMode 是否开启夜间模式
     */
    public static void setNightMode(Context context, boolean isNightMode) {
        sUserInfoBean.setNightMode(isNightMode);
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * @return 是否是夜间模式
     */
    public static boolean isNightMode(Context context) {
        boolean isNightMode;
        if (sUserInfoBean != null) {
            isNightMode = sUserInfoBean.isNightMode();
        } else {
            isNightMode = getUserInfoBean(context).isNightMode();
        }
        return isNightMode;
    }
}
