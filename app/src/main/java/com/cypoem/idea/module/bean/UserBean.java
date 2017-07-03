package com.cypoem.idea.module.bean;

import com.cypoem.idea.module.BasicResponse;

import java.io.Serializable;

/**
 * Created by zhpan on 2017/7/2.
 */

public class UserBean extends BasicResponse<UserBean> implements Serializable {

    /**
     * uid : 2017060025
     * pen_name : Fjgfh
     * sex : 0.517857
     * icon : /cys/upload\20170701044924sss.png
     * address : 贵州省 安顺市 西秀区
     * phone : null
     * birthday : 2017-06-30
     * reg_date : 2017-06-20 08:31:38.0
     * user_status : 1
     * password : null
     * cover_photo : null
     * introduction : 没错，我就是网球王子
     * dictum :
     * watchMeCount : 2
     * myWatchCount : 4
     * enjoy_count : 0
     * keep_count : 3
     */

    private String uid;
    private String pen_name;
    private String sex;
    private String icon;
    private String address;
    private String phone;
    private String birthday;
    private String reg_date;
    private String user_status;
    private String password;
    private Object cover_photo;
    private String introduction;
    private String dictum;
    private int watchMeCount;
    private int myWatchCount;
    private int enjoy_count;
    private int keep_count;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPen_name() {
        return pen_name;
    }

    public void setPen_name(String pen_name) {
        this.pen_name = pen_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(Object cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDictum() {
        return dictum;
    }

    public void setDictum(String dictum) {
        this.dictum = dictum;
    }

    public int getWatchMeCount() {
        return watchMeCount;
    }

    public void setWatchMeCount(int watchMeCount) {
        this.watchMeCount = watchMeCount;
    }

    public int getMyWatchCount() {
        return myWatchCount;
    }

    public void setMyWatchCount(int myWatchCount) {
        this.myWatchCount = myWatchCount;
    }

    public int getEnjoy_count() {
        return enjoy_count;
    }

    public void setEnjoy_count(int enjoy_count) {
        this.enjoy_count = enjoy_count;
    }

    public int getKeep_count() {
        return keep_count;
    }

    public void setKeep_count(int keep_count) {
        this.keep_count = keep_count;
    }
}
