package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/5/27.
 */

public class UserInfoBean {
    /**
     * 是否已经登录
     */
    public boolean isLogin;


    /** 启动状态 */
    private int protectStatus;

    /**
     * 网络请求会话ID
     */
    public String tokenId = "";

    /**
     * 客户端系统类型
     */
    public String osType = "";

    /**
     * 客户端设备版本号
     */
    public String osVersion = "";

    /** 手机型号 */
    public String brand = "";

    /** 用户在服务端的SessionID */
    public String sessionId = "";


    /** 手机唯一标识 */
    public String imei = "";

    /**
     * 用户账号名
     */
    public String userAccount = "";

    public String password;

    public String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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


    public int getProtectStatus() {
        return protectStatus;
    }

    public void setProtectStatus(int protectStatus) {
        this.protectStatus = protectStatus;
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