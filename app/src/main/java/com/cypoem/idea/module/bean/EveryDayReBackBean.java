package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/7/2.
 */

public class EveryDayReBackBean {

    /**
     * total : 1
     * everySay : [{"uid":1,"content":"冰洁该减肥了","photo":null,"publish_time":"2017-07-01 18:44:46.0","pen_name":"韩三少","user":{"uid":"2017060003","pen_name":"三三","watchMeCount":0,"myWatchCount":0,"enjoy_count":0,"keep_count":0},"status":1}]
     */

    private int total;
    private List<EverySayBean> everySay;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EverySayBean> getEverySay() {
        return everySay;
    }

    public void setEverySay(List<EverySayBean> everySay) {
        this.everySay = everySay;
    }

    public static class EverySayBean {
        /**
         * uid : 1
         * content : 冰洁该减肥了
         * photo : null
         * publish_time : 2017-07-01 18:44:46.0
         * pen_name : 韩三少
         * user : {"uid":"2017060003","pen_name":"三三","watchMeCount":0,"myWatchCount":0,"enjoy_count":0,"keep_count":0}
         * status : 1
         */

        private int uid;
        private String content;
        private Object photo;
        private String publish_time;
        private String pen_name;
        private UserBean user;
        private int status;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getPhoto() {
            return photo;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getPen_name() {
            return pen_name;
        }

        public void setPen_name(String pen_name) {
            this.pen_name = pen_name;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class UserBean {
            /**
             * uid : 2017060003
             * pen_name : 三三
             * watchMeCount : 0
             * myWatchCount : 0
             * enjoy_count : 0
             * keep_count : 0
             */

            private String uid;
            private String pen_name;
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
    }
}
