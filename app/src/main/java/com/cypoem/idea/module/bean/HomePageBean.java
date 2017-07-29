package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/7/2.
 */

public class HomePageBean {


    /**
     * write_id : 1000000001
     * pic : /cys/upload/pingfan.jpg
     * user_id : 2017060001
     * write_name : 《平凡的世界》
     * delivery_time : 2017-06-20 10:05:59.0
     * reStatus : 1
     * upStatus : 1
     * section_count : 3
     * like_count : 0
     * read_count : 0
     * type : 校园 悬疑
     * user : {"user_id":2017060001,"pen_name":"浅希"}
     */

    private String write_id;
    private String pic;
    private long user_id;
    private String write_name;
    private String delivery_time;
    private String reStatus;
    private String upStatus;
    private int section_count;
    private int like_count;
    private int read_count;
    private String type;
    private UserBean user;

    public String getWrite_id() {
        return write_id;
    }

    public void setWrite_id(String write_id) {
        this.write_id = write_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * user_id : 2017060001
         * pen_name : 浅希
         */

        private int user_id;
        private String pen_name;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPen_name() {
            return pen_name;
        }

        public void setPen_name(String pen_name) {
            this.pen_name = pen_name;
        }
    }
}
