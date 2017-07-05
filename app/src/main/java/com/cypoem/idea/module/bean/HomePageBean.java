package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/7/2.
 */

public class HomePageBean {

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
         * user : {"uid":"2017060001","pen_name":"浅希","reg_date":"2017-06-20 08:31:38.0","introduction":"不喜欢踌躇满志，不喜欢悲花伤秋，只希望抱着浅浅希望的我。。。。。。。额，其实是个有点精分的人类！！","watchMeCount":0,"myWatchCount":0,"enjoy_count":0,"keep_count":0}
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
        private UserBean user;

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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * uid : 2017060001
             * pen_name : 浅希
             * reg_date : 2017-06-20 08:31:38.0
             * introduction : 不喜欢踌躇满志，不喜欢悲花伤秋，只希望抱着浅浅希望的我。。。。。。。额，其实是个有点精分的人类！！
             * watchMeCount : 0
             * myWatchCount : 0
             * enjoy_count : 0
             * keep_count : 0
             */

            private String uid;
            private String pen_name;
            private String reg_date;
            private String introduction;
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

            public String getReg_date() {
                return reg_date;
            }

            public void setReg_date(String reg_date) {
                this.reg_date = reg_date;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
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
