package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/9/21.
 */

public class HomeBean {

    private List<TopicsBean> topics;
    private List<SubjectsBean> subjects;
    private List<WritesBean> writes;

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public List<WritesBean> getWrites() {
        return writes;
    }

    public void setWrites(List<WritesBean> writes) {
        this.writes = writes;
    }

    public static class TopicsBean extends BaseOpusBean {

        public TopicsBean() {
            setType(2);
        }

        /**
         * create_time : 2017-08-31
         * status : 1
         * topic_id : 7
         * topic_name : 一句话让《春风十里不如你》大结局
         */

        private String create_time;
        private String status;
        private int topic_id;
        private String topic_name;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }
    }

    public static class SubjectsBean extends BaseOpusBean {

        public SubjectsBean() {
            setType(1);
        }

        /**
         * isUpStatus : 1
         * subject_id : 1
         * subject_name : 儿时的记忆
         * subject_pic : /cys/upload/pingfan.jpg
         * subject_status : 1
         * subject_time : 2017-09-20 08:00:16
         */


        private String isUpStatus;
        private int subject_id;
        private String subject_name;
        private String subject_pic;
        private String subject_status;
        private String subject_time;

        public String getIsUpStatus() {
            return isUpStatus;
        }

        public void setIsUpStatus(String isUpStatus) {
            this.isUpStatus = isUpStatus;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getSubject_name() {
            return subject_name;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public String getSubject_pic() {
            return subject_pic;
        }

        public void setSubject_pic(String subject_pic) {
            this.subject_pic = subject_pic;
        }

        public String getSubject_status() {
            return subject_status;
        }

        public void setSubject_status(String subject_status) {
            this.subject_status = subject_status;
        }

        public String getSubject_time() {
            return subject_time;
        }

        public void setSubject_time(String subject_time) {
            this.subject_time = subject_time;
        }
    }

    public static class WritesBean extends BaseOpusBean {

        /**
         * category_id : 5
         * category_name : 影视相关
         * delivery_time : 2017-09-05
         * introduction : 肖生克的救赎
         * like_count : 2
         * pic : /cys/upload/img/write/uid/20170905223822282肖申克.jpg
         * reStatus : 1
         * read_count : 0
         * section_count : 1
         * status : 1
         * subject_id : 0
         * upStatus : 1
         * user : {"balance":0,"create_count":0,"enjoy_count":0,"income_count":0,"input_money_count":0,"keep_count":0,"myWatchCount":0,"my_count":0,"output_money_count":0,"pen_name":"MO。","rollout_count":0,"start_count":0,"user_id":201707210045,"watchMeCount":0}
         * user_id : 201707210045
         * write_id : 1000000327
         * write_name : 肖生克的救赎
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
        private long user_id;
        private int write_id;
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

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getWrite_id() {
            return write_id;
        }

        public void setWrite_id(int write_id) {
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
            private String user_id;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
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
