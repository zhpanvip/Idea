package com.cypoem.idea.module.bean;

import com.cypoem.idea.module.BasicResponse;

/**
 * Created by zhpan on 2017/7/2.
 */

public class RegisterBean extends BasicResponse<RegisterBean> {
    /**
     * uid : 2017070001
     * pen_name : null
     * sex : null
     * icon : /cys/upload\20170701055515favicon.ico
     * address : null
     * birthday : null
     * reg_date : 2017-07-01 17:55:16.0
     * user_status : 1
     * cover_photo : null
     * introduction : null
     * dictum : null
     * watchMeCount : 0
     * myWatchCount : 0
     * enjoy_count : 0
     * keep_count : 0
     */

    private String uid;
    private Object pen_name;
    private Object sex;
    private String icon;
    private Object address;
    private Object birthday;
    private String reg_date;
    private String user_status;
    private Object cover_photo;
    private Object introduction;
    private Object dictum;
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

    public Object getPen_name() {
        return pen_name;
    }

    public void setPen_name(Object pen_name) {
        this.pen_name = pen_name;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
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

    public Object getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(Object cover_photo) {
        this.cover_photo = cover_photo;
    }

    public Object getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Object introduction) {
        this.introduction = introduction;
    }

    public Object getDictum() {
        return dictum;
    }

    public void setDictum(Object dictum) {
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
