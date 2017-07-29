package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/7/8.
 */

public class ArticleBean {

    /*private List<ArticleBean.ResultBean> results;

    public List<ArticleBean.ResultBean> getResults() {
        return results;
    }

    public void setResults(List<ArticleBean.ResultBean> results) {
        this.results = results;
    }*/

    /**
     * uid : 10000000010001000001
     * section_name : 《平凡的世界》续写 ——孙少平
     * content :  车站口，惠英牵着明明冲向少平，在他面前站定。明明看见毁容的少平....
     * create_time : 2017-06-20 10:38:06.0
     * status : 1
     * parent_id : 000
     * section_id : 0001
     * myself_id : 001
     * write_id : 1000000001
     * user_id : 2017060001
     * enjoy_count : 0
     * like_count : 4
     * read_count : 0
     * share_count : 0
     * comment_count : 8
     * user : {"uid":"2017060001","pen_name":"浅希","reg_date":"2017-06-20 08:31:38.0","introduction":"不喜欢踌躇满志，不喜欢悲花伤秋，只希望抱着浅浅希望的我。。。。。。。额，其实是个有点精分的人类！！","watchMeCount":0,"myWatchCount":0,"enjoy_count":0,"keep_count":0}
     * reCount : 2
     * upCount : 0
     * watchStatus : 1
     * keepStatus : 1
     * likeStatus : 1
     */

    private String uid;
    private String section_name;
    private String content;
    private String create_time;
    private String status;
    private String parent_id;
    private String section_id;
    private String myself_id;
    private String write_id;
    private String user_id;
    private int enjoy_count;
    private int like_count;
    private int read_count;
    private int share_count;
    private int comment_count;
    private UserBean user;
    private int reCount;
    private int upCount;
    private int watch_status;
    private String keepStatus;
    private String likeStatus;

    public int getWatch_status() {
        return watch_status;
    }

    public void setWatch_status(int watch_status) {
        this.watch_status = watch_status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getMyself_id() {
        return myself_id;
    }

    public void setMyself_id(String myself_id) {
        this.myself_id = myself_id;
    }

    public String getWrite_id() {
        return write_id;
    }

    public void setWrite_id(String write_id) {
        this.write_id = write_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getEnjoy_count() {
        return enjoy_count;
    }

    public void setEnjoy_count(int enjoy_count) {
        this.enjoy_count = enjoy_count;
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

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getReCount() {
        return reCount;
    }

    public void setReCount(int reCount) {
        this.reCount = reCount;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public String getKeepStatus() {
        return keepStatus;
    }

    public void setKeepStatus(String keepStatus) {
        this.keepStatus = keepStatus;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
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

        private String user_id;
        private String pen_name;
        private String reg_date;
        private String introduction;
        private int watchMeCount;
        private int myWatchCount;
        private int enjoy_count;
        private int keep_count;
        private String icon;
        private boolean isFollow;

        public boolean isFollow() {
            return isFollow;
        }

        public void setFollow(boolean follow) {
            isFollow = follow;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUserId() {
            return user_id;
        }

        public void setUid(String user_id) {
            this.user_id = user_id;
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
