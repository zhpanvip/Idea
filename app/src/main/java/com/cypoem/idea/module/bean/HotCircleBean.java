package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/12/20.
 */

public class HotCircleBean {

    /**
     * category : 情感
     * circleId : 85
     * date : 2017-12-10 19:04
     * icon : /cys/upload/img/circle/uid/20171210190450871sss.jpg
     * introduction : 总有那么一个地方，一个人，让你心动，留恋不已。总有那么一个人，一句对不起，让你心痛，刻骨铭心。有一次，你说爱我。那一瞬间，我以为是永远。
     * isFollow : 0
     * name : 情书
     * privateStoryCount : 7
     * publicStoryCount : 0
     * shareCount : 0
     * status : 1
     * stick : 0
     * storyCount : 7
     * type : 2
     * user : {"icon":"/cys/upload/img/user/uid/icon/20171215205506451sss.jpg","pen_name":"一首二进制的歌","user_id":2017060003}
     * userCount : 2
     * writes : [{"delivery_time":"2017-12-10 19:13","introduction":"我熬夜两天写的一封情书，却让女神和别人好上了。","isAddPic":"0","like_count":4,"read_count":0,"status":"1","storyLables":[{"lableId":19,"lableName":"操蛋"}],"type":"1","user":{"icon":"/cys/upload/img/user/uid/icon/20171215205506451sss.jpg","pen_name":"一首二进制的歌","user_id":2017060003},"userCount":1,"writeStoryCount":2,"write_id":1000000507,"write_name":"情书之外"},{"delivery_time":"2017-12-10 19:41","introduction":"你问我出生前在做什么\n我答 我在天上挑妈妈","isAddPic":"1","like_count":2,"pic":"/cys/upload/img/write/uid/20171210194104293sss.jpg","read_count":0,"status":"1","storyLables":[{"lableId":20,"lableName":"情感"}],"type":"1","user":{"icon":"/cys/upload/img/user/uid/icon/20171215205506451sss.jpg","pen_name":"一首二进制的歌","user_id":2017060003},"userCount":1,"writeStoryCount":2,"write_id":1000000508,"write_name":"挑妈妈"},{"delivery_time":"2017-12-10 22:46","introduction":"我好怕，因为我没有写过情书，也不知道怎么样让你可以体会现在这个真真正正的我，对于你，我怎么总是感觉还没有拥有就失去了呢？我现在的感觉是无助和彷徨，有太多的话想和你说，我是那么用心的刻意隐藏，我是真的真的想在你的心里有一个特别的位置\u2026\u2026","isAddPic":"0","like_count":2,"read_count":0,"status":"1","storyLables":[{"lableId":18,"lableName":"真心"},{"lableId":20,"lableName":"情感"}],"type":"1","user":{"icon":"/cys/upload/img/user/uid/icon/20171219192845155sss.jpg","pen_name":"就你丑","user_id":212345689914},"userCount":1,"writeStoryCount":2,"write_id":1000000510,"write_name":"我不会写情书"}]
     */

    private String category;
    private int circleId;
    private String date;
    private String icon;
    private String introduction;
    private String isFollow;
    private String name;
    private int privateStoryCount;
    private int publicStoryCount;
    private int shareCount;
    private int status;
    private int stick;
    private int storyCount;
    private String type;
    private UserBean user;
    private int userCount;
    private List<WriteBean> writes;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrivateStoryCount() {
        return privateStoryCount;
    }

    public void setPrivateStoryCount(int privateStoryCount) {
        this.privateStoryCount = privateStoryCount;
    }

    public int getPublicStoryCount() {
        return publicStoryCount;
    }

    public void setPublicStoryCount(int publicStoryCount) {
        this.publicStoryCount = publicStoryCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStick() {
        return stick;
    }

    public void setStick(int stick) {
        this.stick = stick;
    }

    public int getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(int storyCount) {
        this.storyCount = storyCount;
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

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<WriteBean> getWrites() {
        return writes;
    }

    public void setWrites(List<WriteBean> writes) {
        this.writes = writes;
    }

    public static class UserBean {
        /**
         * icon : /cys/upload/img/user/uid/icon/20171215205506451sss.jpg
         * pen_name : 一首二进制的歌
         * user_id : 2017060003
         */

        private String icon;
        private String pen_name;
        private int user_id;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPen_name() {
            return pen_name;
        }

        public void setPen_name(String pen_name) {
            this.pen_name = pen_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

}
