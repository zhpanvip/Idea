package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/11/25.
 */

public class CircleBean extends SearchBaseBean {
    /**
     * category : 技术
     * circleId : 9
     * date : 2017-10-23 10:28
     * icon : /cys/upload/img/circle/uid/20171025104648369一条狗的使命.jpg
     * introduction : 技术交流圈
     * name : 聊骚圈
     * privateStoryCount : 0
     * publicStoryCount : 0
     * shareCount : 0
     * status : 1
     * stick : 0
     * storyCount : 0
     * type : 2
     * user : {"icon":"/cys/upload/img/user/uid/icon/20170912174506318sss.png","pen_name":"棉花草","user_id":2017060025}
     * userCount : 0
     */

    private String category;
    private int circleId;
    private String date;
    private String icon;
    private String introduction;
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
}
