package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/8/11.
 */

public class BannerBean {


    /**
     * banner_id : 2
     * url : null
     * write : {"write_id":1000000002,"user_id":2017060004}
     * banner_pic : /cys/upload/baigujing.jpg
     * banner_status : null
     * type : 1
     */

    private int banner_id;
    private String url;
    private WriteBean write;
    private String banner_pic;
    private String banner_status;
    private String type;

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WriteBean getWrite() {
        return write;
    }

    public void setWrite(WriteBean write) {
        this.write = write;
    }

    public String getBanner_pic() {
        return banner_pic;
    }

    public void setBanner_pic(String banner_pic) {
        this.banner_pic = banner_pic;
    }

    public String getBanner_status() {
        return banner_status;
    }

    public void setBanner_status(String banner_status) {
        this.banner_status = banner_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class WriteBean {
        /**
         * write_id : 1000000002
         * user_id : 2017060004
         */

        private long write_id;
        private long user_id;

        public long getWrite_id() {
            return write_id;
        }

        public void setWrite_id(long write_id) {
            this.write_id = write_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }
    }
}
