package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/7/8.
 */

public class OpusBean {

    /**
     * uid : 1000000001
     * pic : /cys/upload201706200902551.jpg
     * user_id : 2017060001
     * write_name : 《平凡的世界》
     * delivery_time : 2017-06-20 10:05:59.0
     * status : 1
     * reStatus : 1
     * upStatus : 1
     * section_count : 2
     * like_count : 4
     * read_count : 0
     * type : 校园 悬疑
     */

    private String uid;
    private String pic;
    private String user_id;
    private String write_name;
    private String delivery_time;
    private String status;
    private String reStatus;
    private String upStatus;
    private int section_count;
    private int like_count;
    private int read_count;
    private String type;
    private String write_id;

    public String getWrite_id() {
        return write_id;
    }

    public void setWrite_id(String write_id) {
        this.write_id = write_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWrite_name() {
        return write_name;
    }

    public void setWrite_name(String write_name) {
        this.write_name = write_name;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReStatus() {
        return reStatus;
    }

    public void setReStatus(String reStatus) {
        this.reStatus = reStatus;
    }

    public String getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(String upStatus) {
        this.upStatus = upStatus;
    }

    public int getSection_count() {
        return section_count;
    }

    public void setSection_count(int section_count) {
        this.section_count = section_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
