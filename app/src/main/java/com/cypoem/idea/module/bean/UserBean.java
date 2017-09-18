package com.cypoem.idea.module.bean;

/**
 * Created by zhpan on 2017/7/2.
 */

public class UserBean {

        /**
         * uid : 2017060025
         * pen_name : Fjgfh
         * sex : 0.517857
         * icon : /cys/upload\20170701044924sss.png
         * address : 贵州省 安顺市 西秀区
         * phone : null
         * birthday : 2017-06-30
         * reg_date : 2017-06-20 08:31:38.0
         * user_status : 1
         * password : null
         * cover_photo : null
         * introduction : 没错，我就是网球王子
         * dictum :
         * watchMeCount : 2
         * myWatchCount : 4
         * enjoy_count : 0
         * keep_count : 3
         */

    private String user_id="";
    private String pen_name;
    private String sex;
    private String icon;
    private String address;
    private String phone;
    private String birthday;
    private String reg_date;
    private String user_status;
    private String password="";
    private String cover_photo;
    private String introduction;
    private String dictum;
    private int watchMeCount;
    private int myWatchCount;
    private int enjoy_count;
    private int keep_count;
    private String return_code;
    private int watch_status;
    private String msg;
    private boolean follow;
    /**
     * balance : 0
     * create_count : 0
     * income_count : 0
     * input_money_count : 0
     * my_count : 0
     * output_money_count : 0
     * rollout_count : 0
     * start_count : 0
     * user_id : 212345689788
     * wx_ob : sadsfefqwrwfr
     */

    private int balance;
    private int create_count;
    private int income_count;
    private int input_money_count;
    private int my_count;
    private int output_money_count;
    private int rollout_count;
    private int start_count;
    private String wx_ob;


    public int getWatch_status() {
        return watch_status;
    }

    public void setWatch_status(int watch_status) {
        this.watch_status = watch_status;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getPen_name() {
        return pen_name;
    }

    public void setPen_name(String pen_name) {
        this.pen_name = pen_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDictum() {
        return dictum;
    }

    public void setDictum(String dictum) {
        this.dictum = dictum;
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

    public String getWx_ob() {
        return wx_ob;
    }

    public void setWx_ob(String wx_ob) {
        this.wx_ob = wx_ob;
    }
}
