package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/5/27.
 */

public class UserInfoBean {
    /**
     * 是否已经登录
     */
    private boolean isLogin;



    /**
     * 网络请求会话ID
     */
    private String tokenId = "";

    /**
     * 客户端系统类型
     */
    private String osType = "";

    /**
     * 客户端设备版本号
     */
    private String osVersion = "";

    /**
     * 手机型号
     */
    private String brand = "";

    /**
     * 用户在服务端的SessionID
     */
    private String sessionId = "";

    /**
     * 手机唯一标识
     */
    private String imei = "";

    /**
     * 用户账号名
     */
    private String userAccount = "";

    public String password;

    /**
     * 笔名
     */
    private String penName;

    /**
     * 是否开启夜间模式
     */
    private boolean isNightMode;

    public boolean isNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
