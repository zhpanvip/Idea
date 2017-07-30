package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/7/30.
 */

public class CommentBean {


    /**
     * comment_id : 11
     * content : 你好
     * section_id : 1000000000000010
     * like_count : 0
     * time : 2017-07-30 14:05:09.0
     * user : {"user_id":2017060003,"pen_name":"韩三少","icon":"/cys/upload/201707260938sss.png"}
     * like_status : 0
     */

    private String comment_id;
    private String content;
    private String section_id;
    private int like_count;
    private String time;
    private UserBean user;
    private String like_status;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getLike_status() {
        return like_status;
    }

    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

    public static class UserBean {
        /**
         * user_id : 2017060003
         * pen_name : 韩三少
         * icon : /cys/upload/201707260938sss.png
         */

        private String user_id;
        private String pen_name;
        private String icon;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPen_name() {
            return pen_name;
        }

        public void setPen_name(String pen_name) {
            this.pen_name = pen_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
