package com.cypoem.idea.module.bean;

import java.util.List;

/**
 * Created by zhpan on 2017/12/6.
 */

public class DiscoverBean {

    private List<HostCirclesBean> hostCircles;
    private List<HostWritesBean> hostWrites;
    private List<BannersBean> banners;
    private List<UsersBean> users;

    public List<HostCirclesBean> getHostCircles() {
        return hostCircles;
    }

    public void setHostCircles(List<HostCirclesBean> hostCircles) {
        this.hostCircles = hostCircles;
    }

    public List<HostWritesBean> getHostWrites() {
        return hostWrites;
    }

    public void setHostWrites(List<HostWritesBean> hostWrites) {
        this.hostWrites = hostWrites;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class HostCirclesBean {
        /**
         * category : 技术
         * circleId : 9
         * date : 2017-10-23 10:28
         * icon : /cys/upload/img/circle/uid/20171025104648369一条狗的使命.jpg
         * introduction : 技术交流圈
         * isFollow : 1
         * name : 聊骚圈
         * privateStoryCount : 15
         * publicStoryCount : 1
         * shareCount : 0
         * status : 1
         * stick : 0
         * storyCount : 16
         * type : 2
         * user : {"icon":"/cys/upload/img/user/uid/icon/20170912174506318sss.png","pen_name":"棉花草","user_id":2017060025}
         * userCount : 0
         * writes : [{"delivery_time":"2017-06-20 00:00","introduction":"风尘误","isAddPic":"1","like_count":11,"pic":"/cys/upload/fengchen.jpg","read_count":736,"status":"1","storyLables":[{"lableId":5,"lableName":"玄幻"}],"type":"1","user":{"icon":"/cys/upload/img/user/uid/icon/2017082417210718head_pic","pen_name":"冉夏","user_id":2017060013},"writeStoryCount":5,"write_id":1000000005,"write_name":"《风尘误》"}]
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
        private String status;
        private int stick;
        private int storyCount;
        private String type;
        private UserBean user;
        private int userCount;
        private List<WritesBean> writes;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

        public List<WritesBean> getWrites() {
            return writes;
        }

        public void setWrites(List<WritesBean> writes) {
            this.writes = writes;
        }

        public static class UserBean {
            /**
             * icon : /cys/upload/img/user/uid/icon/20170912174506318sss.png
             * pen_name : 棉花草
             * user_id : 2017060025
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

        public static class WritesBean {
            /**
             * delivery_time : 2017-06-20 00:00
             * introduction : 风尘误
             * isAddPic : 1
             * like_count : 11
             * pic : /cys/upload/fengchen.jpg
             * read_count : 736
             * status : 1
             * storyLables : [{"lableId":5,"lableName":"玄幻"}]
             * type : 1
             * user : {"icon":"/cys/upload/img/user/uid/icon/2017082417210718head_pic","pen_name":"冉夏","user_id":2017060013}
             * writeStoryCount : 5
             * write_id : 1000000005
             * write_name : 《风尘误》
             */

            private String delivery_time;
            private String introduction;
            private String isAddPic;
            private int like_count;
            private String pic;
            private int read_count;
            private String status;
            private String type;
            private UserBeanX user;
            private int writeStoryCount;
            private int write_id;
            private String write_name;
            private List<StoryLablesBean> storyLables;

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

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
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

            public List<StoryLablesBean> getStoryLables() {
                return storyLables;
            }

            public void setStoryLables(List<StoryLablesBean> storyLables) {
                this.storyLables = storyLables;
            }

            public static class UserBeanX {
                /**
                 * icon : /cys/upload/img/user/uid/icon/2017082417210718head_pic
                 * pen_name : 冉夏
                 * user_id : 2017060013
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

            public static class StoryLablesBean {
                /**
                 * lableId : 5
                 * lableName : 玄幻
                 */

                private int lableId;
                private String lableName;

                public int getLableId() {
                    return lableId;
                }

                public void setLableId(int lableId) {
                    this.lableId = lableId;
                }

                public String getLableName() {
                    return lableName;
                }

                public void setLableName(String lableName) {
                    this.lableName = lableName;
                }
            }
        }
    }

    public static class HostWritesBean {
        /**
         * delivery_time : 2017-08-05 00:00
         * introduction : ...
         * isAddPic : 1
         * like_count : 54
         * pic : /cys/upload/img/write/uid/heshen.jpg
         * read_count : 17866
         * status : 1
         * type : 1
         * user : {"icon":"/cys/upload/img/user/uid/icon/20170903010635190sss.png","pen_name":"佚名","user_id":201707210007}
         * userCount : 3
         * userIsFollowWrite : 0
         * writeStoryCount : 1
         * write_id : 1000000039
         * write_name : 河神：鬼水怪谈
         */

        private String delivery_time;
        private String introduction;
        private String isAddPic;
        private int like_count;
        private String pic;
        private int read_count;
        private String status;
        private String type;
        private UserBeanXX user;
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

        public UserBeanXX getUser() {
            return user;
        }

        public void setUser(UserBeanXX user) {
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

        public static class UserBeanXX {
            /**
             * icon : /cys/upload/img/user/uid/icon/20170903010635190sss.png
             * pen_name : 佚名
             * user_id : 201707210007
             */

            private String icon;
            private String pen_name;
            private long user_id;

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

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }
    }

    public static class BannersBean {
        /**
         * banner_id : 1
         * banner_pic : /cysht/upload/img/banner/uid/new_intro.png
         * type : 0
         * url : http://www.cypoem.com/index_intro3.html
         * write : {"user_id":2017060004,"write_id":1000000002}
         */

        private int banner_id;
        private String banner_pic;
        private String type;
        private String url;
        private WriteBean write;

        public int getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(int banner_id) {
            this.banner_id = banner_id;
        }

        public String getBanner_pic() {
            return banner_pic;
        }

        public void setBanner_pic(String banner_pic) {
            this.banner_pic = banner_pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public static class WriteBean {
            /**
             * user_id : 2017060004
             * write_id : 1000000002
             */

            private int user_id;
            private int write_id;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getWrite_id() {
                return write_id;
            }

            public void setWrite_id(int write_id) {
                this.write_id = write_id;
            }
        }
    }

    public static class UsersBean {
        /**
         * enjoy_count : 0
         * icon : /cys/upload/img/user/uid/icon/20171113222932162不依诗集.jpg
         * my_count : 0
         * pen_name : 匿名
         * user_id : 212345689839
         * watch_status : 0
         */

        private int enjoy_count;
        private String icon;
        private int my_count;
        private String pen_name;
        private long user_id;
        private int watch_status;

        public int getEnjoy_count() {
            return enjoy_count;
        }

        public void setEnjoy_count(int enjoy_count) {
            this.enjoy_count = enjoy_count;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getMy_count() {
            return my_count;
        }

        public void setMy_count(int my_count) {
            this.my_count = my_count;
        }

        public String getPen_name() {
            return pen_name;
        }

        public void setPen_name(String pen_name) {
            this.pen_name = pen_name;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public int getWatch_status() {
            return watch_status;
        }

        public void setWatch_status(int watch_status) {
            this.watch_status = watch_status;
        }
    }
}
