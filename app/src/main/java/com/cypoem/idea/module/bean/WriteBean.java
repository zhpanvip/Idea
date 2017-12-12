package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/12/12.
 */

public class WriteBean  extends SearchBaseBean{

    /**
     * delivery_time : 2017-06-20 00:00
     * introduction : 木淑清是一个先天性盲人，这样看不到阳光的日子已经陪伴了她整整十七年。至于先天性失明的原因，大概是她的母亲在怀她时犯了什么禁忌。比如喝酒，吃药之类的。早些年间，她的父母走访了许多大医院企图治好她的病，可
     * isAddPic : 1
     * like_count : 2
     * pic : /cys/upload/jiaru.jpg
     * read_count : 36
     * status : 1
     * type : 2
     * user : {"icon":"/cys/upload/icon.jpg","pen_name":"三季人","user_id":2017060022}
     * userCount : 1
     * userIsFollowWrite : 0
     * writeStoryCount : 1
     * write_id : 1000000015
     * write_name : 《假如给我三天光明》
     */

    private String delivery_time;
    private String introduction;
    private String isAddPic;
    private int like_count;
    private String pic;
    private int read_count;
    private String status;
    private String type;
    private UserBean user;
    private int userCount;
    private String userIsFollowWrite;
    private int writeStoryCount;
    private int write_id;
    private String write_name;

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

    public String getIsAddPic() {
        return isAddPic;
    }

    public void setIsAddPic(String isAddPic) {
        this.isAddPic = isAddPic;
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

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserIsFollowWrite() {
        return userIsFollowWrite;
    }

    public void setUserIsFollowWrite(String userIsFollowWrite) {
        this.userIsFollowWrite = userIsFollowWrite;
    }

    public int getWriteStoryCount() {
        return writeStoryCount;
    }

    public void setWriteStoryCount(int writeStoryCount) {
        this.writeStoryCount = writeStoryCount;
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

}
