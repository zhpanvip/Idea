package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/9/22.
 */

public class SubjectBean {

    private List<WritesBean> writes;

    public List<WritesBean> getWrites() {
        return writes;
    }

    public void setWrites(List<WritesBean> writes) {
        this.writes = writes;
    }

    public static class WritesBean {
        /**
         * category_id : 200
         * category_name : 自定义
         * delivery_time : 2017-09-21
         * introduction : 测试
         * like_count : 0
         * pic : /cys/upload/img/write/uid/20170921215939330new-大学专题1.jpg
         * reStatus : 1
         * read_count : 0
         * section_count : 5
         * status : 1
         * subject_id : 18
         * upStatus : 1
         * user : {"balance":0,"create_count":0,"enjoy_count":0,"income_count":0,"input_money_count":0,"keep_count":0,"myWatchCount":0,"my_count":0,"output_money_count":0,"pen_name":"MO。","rollout_count":0,"start_count":0,"user_id":201707210045,"watchMeCount":0}
         * user_id : 201707210045
         * write_id : 1000000353
         * write_name : 测试
         */

        private int category_id;
        private String category_name;
        private String delivery_time;
        private String introduction;
        private int like_count;
        private String pic;
        private String reStatus;
        private int read_count;
        private int section_count;
        private String status;
        private int subject_id;
        private String upStatus;
        private UserBean user;
        private String user_id;
        private String write_id;
        private String write_name;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getReStatus() {
            return reStatus;
        }

        public void setReStatus(String reStatus) {
            this.reStatus = reStatus;
        }

        public int getRead_count() {
            return read_count;
        }

        public void setRead_count(int read_count) {
            this.read_count = read_count;
        }

        public int getSection_count() {
            return section_count;
        }

        public void setSection_count(int section_count) {
            this.section_count = section_count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getUpStatus() {
            return upStatus;
        }

        public void setUpStatus(String upStatus) {
            this.upStatus = upStatus;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getWrite_id() {
            return write_id;
        }

        public void setWrite_id(String write_id) {
            this.write_id = write_id;
        }

        public String getWrite_name() {
            return write_name;
        }

        public void setWrite_name(String write_name) {
            this.write_name = write_name;
        }

        public static class UserBean {
            /**
             * balance : 0
             * create_count : 0
             * enjoy_count : 0
             * income_count : 0
             * input_money_count : 0
             * keep_count : 0
             * myWatchCount : 0
             * my_count : 0
             * output_money_count : 0
             * pen_name : MO。
             * rollout_count : 0
             * start_count : 0
             * user_id : 201707210045
             * watchMeCount : 0
             */

            private int balance;
            private int create_count;
            private int enjoy_count;
            private int income_count;
            private int input_money_count;
            private int keep_count;
            private int myWatchCount;
            private int my_count;
            private int output_money_count;
            private String pen_name;
            private int rollout_count;
            private int start_count;
            private long user_id;
            private int watchMeCount;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public int getCreate_count() {
                return create_count;
            }

            public void setCreate_count(int create_count) {
                this.create_count = create_count;
            }

            public int getEnjoy_count() {
                return enjoy_count;
            }

            public void setEnjoy_count(int enjoy_count) {
                this.enjoy_count = enjoy_count;
            }

            public int getIncome_count() {
                return income_count;
            }

            public void setIncome_count(int income_count) {
                this.income_count = income_count;
            }

            public int getInput_money_count() {
                return input_money_count;
            }

            public void setInput_money_count(int input_money_count) {
                this.input_money_count = input_money_count;
            }

            public int getKeep_count() {
                return keep_count;
            }

            public void setKeep_count(int keep_count) {
                this.keep_count = keep_count;
            }

            public int getMyWatchCount() {
                return myWatchCount;
            }

            public void setMyWatchCount(int myWatchCount) {
                this.myWatchCount = myWatchCount;
            }

            public int getMy_count() {
                return my_count;
            }

            public void setMy_count(int my_count) {
                this.my_count = my_count;
            }

            public int getOutput_money_count() {
                return output_money_count;
            }

            public void setOutput_money_count(int output_money_count) {
                this.output_money_count = output_money_count;
            }

            public String getPen_name() {
                return pen_name;
            }

            public void setPen_name(String pen_name) {
                this.pen_name = pen_name;
            }

            public int getRollout_count() {
                return rollout_count;
            }

            public void setRollout_count(int rollout_count) {
                this.rollout_count = rollout_count;
            }

            public int getStart_count() {
                return start_count;
            }

            public void setStart_count(int start_count) {
                this.start_count = start_count;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public int getWatchMeCount() {
                return watchMeCount;
            }

            public void setWatchMeCount(int watchMeCount) {
                this.watchMeCount = watchMeCount;
            }
        }
    }
}
