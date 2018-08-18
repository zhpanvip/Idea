package com.cypoem.idea.net;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.ArticleListBean;
import com.cypoem.idea.module.bean.BannerBean;
import com.cypoem.idea.module.bean.CircleListBean;
import com.cypoem.idea.module.bean.CircleResponse;
import com.cypoem.idea.module.bean.CreateCircleResponse;
import com.cypoem.idea.module.bean.DiscoverBean;
import com.cypoem.idea.module.bean.CommentBean;
import com.cypoem.idea.module.bean.EverydayReBackBean;
import com.cypoem.idea.module.bean.FansBean;
import com.cypoem.idea.module.bean.LoginResponse;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.PublishBean;
import com.cypoem.idea.module.bean.RankingBean;
import com.cypoem.idea.module.bean.SearchBean;
import com.cypoem.idea.module.bean.SubjectBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.UserList;
import com.cypoem.idea.module.wrapper.ChaptersWrapper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zhpan on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */

    int DEFAULT_TIMEOUT = 3000;

    //String HOST = "http://hansanshao.cn:8080/";
    String HOST = "http://cypoem.com";
    String PORT = ":8080/";
    String API_SERVER_URL = HOST + PORT + "cys/";
    String WEBSITE = "http://www.cypoem.com";


    /**
     * 注册接口
     *
     * @param partList 注册信息
     * @return
     */
    @Multipart
    @POST("user/register.do")
    Observable<BasicResponse<UserBean>> register(@Part List<MultipartBody.Part> partList);


    /**
     * 根据用户id获取用户信息
     *
     * @param viewUserId 查看的用户id
     * @param user_id    当前登录的用户id
     * @return
     */
    @GET("user/viewUserInfo.do")
    Observable<BasicResponse<LoginResponse>> getUserInfo(@Query("watch_user_id") String viewUserId, @Query("user_id") String user_id);


    /**
     * 发现页面
     *
     * @param user_id 用户ID
     * @param page    请求第几页数据
     * @param rows    每页请求几条数据
     * @return
     */
    @GET("circle/firstPage.do")
    Observable<BasicResponse<DiscoverBean>> getDiscover(@Query("user_id") String user_id, @Query("rows") int page, @Query("rows") int rows);


    /**
     * 完善用户信息
     *
     * @param mapUserInfo 用户信息
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateText.do")
    Observable<BasicResponse> updateUserInfo(@FieldMap Map<String, String> mapUserInfo);


    /**
     * 取消关注
     *
     * @param userId  用户id
     * @param focusId 关注用户的id
     * @return
     */
    @POST("watch/delete.do")
    Observable<BasicResponse<String>> cancelFocus(@Query("user_id") String userId, @Query("watch_user_id") String focusId);


    /**
     * 查询我关注我的
     *
     * @param page   显示第几页
     * @param rows   每页显示几条数据
     * @param userId 登陆用户id
     * @param type   1.关注的用户，2。我的粉丝
     * @returnf
     */
    @GET("watch/myWatchUsers.do")
    Observable<BasicResponse<UserList>> getMyFocus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);


    /**
     * 查询圈子分类
     *
     * @return
     */
    @GET("circle/queryCircleCategorys.do")
    Observable<BasicResponse<List<FansBean>>> getCircleClass();

    /**
     * 查询圈子分类
     *
     * @return
     */
    @GET("circle/queryCircleIcons.do")
    Observable<BasicResponse> getCircleIcon();


    /**
     * 创建圈子
     *
     * @return
     */
    @Multipart
    @POST("circle/add.do")
    Observable<BasicResponse<CreateCircleResponse>> createCircle(@Part List<MultipartBody.Part> partList);

    /**
     * 删除圈子
     *
     * @return
     */
    @POST("circle/delete.do")
    Observable<BasicResponse> deleteCircle(@Query("circleId") String circleId);


    /**
     * 我关注的/我创建的圈子
     *
     * @param type 1.关注的圈子 2.创建的圈子
     */

    @GET("circle/findFollowCircles.do")
    Observable<BasicResponse<CircleResponse>> getMyCircle(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);

    /**
     * 查询某个圈子中的作品
     *
     * @param type 1.最热 2.最新
     */

    @GET("circle/queryCircleWrites.do")
    Observable<BasicResponse> getCircleOpus(@Query("circleId") String userId, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);


    @GET("circle/queryById.do")
    Observable<BasicResponse> getCircleById(@Query("user_id") String userId, @Query("circleId") String circleId, @Query("page") int page, @Query("rows") int rows);


    /**
     * @param circleName 圈子名称   type为4时需要
     * @param category   类别名称 type为3时需要
     * @param page
     * @param rows
     * @param type       1.根据热度获取圈子排序 2.根据时间排序 3，根据圈子类别搜索圈子 4，根据圈子名称搜索圈子
     * @return
     */
    @GET("circle/queryCircles.do")
    Observable<BasicResponse<CircleResponse>> getOrderCircle(@Query("page") int page, @Query("rows") int rows, @Query("type") int type, @Query("name") String circleName, @Query("category") String category);

    /**
     * 查询故事标签
     *
     * @return
     */
    @GET("write/findStoryLable.do")
    Observable<BasicResponse> getStoryLable();

    /**
     * 关注/取消关注故事
     *
     * @param type 1.关注 2.取消关注
     * @return
     */
    @GET("write/followWrite.do")
    Observable<BasicResponse> followStory(@Query("user_id") String userId, @Query("write_id") String storyId, @Query("type") int type);

    /**
     * 创建故事
     *
     * @param partList 所需参数
     * @return
     */
    @Multipart
    @POST("write/save.do")
    Observable<BasicResponse<PublishBean>> createStoryWithStory(@Part List<MultipartBody.Part> partList);

    /**
     * 创建故事
     *
     * @param map 所需参数
     * @return
     */
    @FormUrlEncoded
    @POST("write/save.do")
    Observable<BasicResponse<PublishBean>> createStory(@FieldMap Map<String, Object> map);

    /**
     * 通过id查询故事所有章节
     *
     * @param userId
     * @param storyId
     * @return
     */
    @GET("write/queryWriteSections.do")
    Observable<BasicResponse> findStoryById(@Query("user_id") String userId, @Query("write_id") String storyId);

    /**
     * 最新、最热、按照名字搜索的故事，我创建的，我参与的，我关注的故事和我的草稿
     *
     * @param type 1,查询结果为最新故事（默认）；2，查询结果用最热故事；3，查询结果为按照名字搜索到的故事；
     *             4，我关注的；5，我创建的；6，我参与的；7，我的草稿
     * @return
     */
    @GET("write/discover.do")
    Observable<BasicResponse<ArticleListBean>> findStory(@Query("user_id") String userId, @Query("type") int type, @Query("page") int page, @Query("rows") int rows);

    /**
     * 删除草稿
     *
     * @param userId
     * @return
     */
    @POST("write/deleteDraft.do")
    Observable<BasicResponse> deleteDraft(@Query("user_id") String userId, @Query("write_id") int write_id);

    /**
     * 修改草稿/发布草稿
     *
     * @return
     */
    @FormUrlEncoded
    @POST("write/updateWrite.do")
    Observable<BasicResponse> updateStory(@FieldMap Map<String, Object> mapLogin);

    /**
     * 续写章节
     *
     * @param mapLogin
     * @return
     */
    @FormUrlEncoded
    @POST("write/updateWrite.do")
    Observable<BasicResponse> upWrite(@FieldMap Map<String, Object> mapLogin);

    /**
     * 删除章节
     *
     * @return
     */
    @POST("write/deleteSection.do")
    Observable<BasicResponse> deleteChapter(@Query("user_id") String user_id, @Query("section_id") String section_id);

    /**
     * 查看章节详情
     *
     * @param userId
     * @return
     */
    @GET("write/querySectionContent.do")
    Observable<BasicResponse> findChapter(@Query("user_id") String userId, @Query("section_id") int sectionId);

    /**
     * 章节点赞/取消章节点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST("write/updateLikeCount.do")
    Observable<BasicResponse> priseChapter(@FieldMap Map<String, Object> mapLogin);

    @FormUrlEncoded
    @POST("write/messagePush.do")
    Observable<BasicResponse> getPushMessage(@FieldMap Map<String, Object> mapLogin);

    /**
     * 查看消息
     *
     * @return
     */
    @POST("write/queryJpushMsg.do")
    Observable<BasicResponse> findJpushMsg(@Query("user_id") String user_id, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);

    /**
     * 评论章节
     *
     * @param userId    用户id
     * @param sectionId 章节id
     * @param content   评论内容
     * @return
     */
    @POST("comment/add.do")
    Observable<BasicResponse<String>> comment(@Query("user_id") String userId, @Query("section_id") String sectionId, @Query("content") String content);

    @POST("comment/delete.do")
    Observable<BasicResponse<String>> deleteComment(@Query("comment_id") String comment_id, @Query("section_id") String sectionId);

    /**
     * 查看章节评论
     *
     * @return
     */
    @FormUrlEncoded
    @POST("comment/viewComment.do")
    Observable<BasicResponse<List<CommentBean>>> getComment(@FieldMap Map<String, Object> map);


    /**
     * 为评论点赞
     *
     * @param userId     用户id
     * @param comment_id 要点赞的评论的uid
     * @param status     点赞/取消点赞
     * @return
     */
    @POST("comment/updateLike.do")
    Observable<BasicResponse<String>> lightComment(@Query("user_id") String userId, @Query("comment_id") String comment_id, @Query("status") String status);


    /**
     * 排行榜
     *
     * @param userId
     * @param type   1.土豪榜  2.明星排行榜
     * @return
     */
    @GET("user/rankingList.do")
    Observable<BasicResponse<List<RankingBean>>> getRanking(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);

    /**
     * 搜索
     *
     * @param type 1.圈子 故事 人  2.圈子  3.故事  4 人
     * @return
     */
    @GET("circle/search.do")
    Observable<BasicResponse<SearchBean>> search(@Query("user_id") String userId, @Query("name") String name, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);

    @FormUrlEncoded
    @POST("user/nearbyUsers.do")
    Observable<BasicResponse<UserList>> getHotUsers(@FieldMap Map<String, Object> params);


    /**
     * 意见反馈
     *
     * @param adviceMap 意见信息
     * @return
     */
    @FormUrlEncoded
    @POST("advice/add.do")
    Observable<BasicResponse<String>> postAdvice(@FieldMap Map<String, String> adviceMap);

    /**
     * 上传头像/作者照片接口
     *
     * @param userInfo
     * @return
     */
    @Multipart
    @POST("user/updatePic.do")
    Observable<BasicResponse> updateHeadPic(@Part List<MultipartBody.Part> userInfo);


    /**
     * 登陆
     *
     * @param mapLogin 登陆信息
     * @return
     */
    @FormUrlEncoded
    @POST("user/login.do")
    Observable<BasicResponse<LoginResponse>> login(@FieldMap Map<String, Object> mapLogin);

    /**
     * 第三方登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/thirdPartyLogin.do")
    Observable<BasicResponse<UserBean>> thirdPartLogin(@FieldMap Map<String, Object> map);

    /**
     * 忘记密码 通过手机验证修改密码
     *
     * @param phone    手机号
     * @param password 密码
     * @return 修改结果
     */
    @POST("user/updatePwd.do")
    Observable<BasicResponse<String>> updatePsw(@Query("phone") String phone, @Query("password") String password);

    /**
     * 验证该手机号是否注册
     *
     * @param phone 验证的手机号
     * @return
     */
    @GET("user/getUser.do")
    Observable<BasicResponse> isRegistered(@Query("phone") String phone);


    /**
     * 每日一句
     *
     * @param userInfo
     * @return
     */
    @Multipart
    @POST("everySay/add.do")
    Observable<BasicResponse> publishEverydaySay(@Part List<MultipartBody.Part> userInfo);

    /**
     * 每日一句往期回看
     *
     * @param page   当前页数
     * @param number 每页显示的条目
     * @return
     */
    @GET("everySay/selectAll.do")
    Observable<BasicResponse<List<EverydayReBackBean>>> lookBack(@Query("page") int page, @Query("rows") int number);


    /**
     * 获取专题列表
     *
     * @return
     */
    @GET("write/querySubjectWrites.do")
    Observable<BasicResponse<SubjectBean>> getSubject(@Query("subject_id") int subject_id, @Query("page") int page, @Query("rows") int number);


    /**
     * 发布作品
     *
     * @param partList
     * @return
     */
    @Multipart
    @POST("write/add.do")
    Observable<BasicResponse<PublishBean>> publishOpus(@Part List<MultipartBody.Part> partList);

    /**
     * 添加章节
     *
     * @param partList 添加章节参数集合
     * @return
     */
    @Multipart
    @POST("section/add.do")
    Observable<BasicResponse> addChapter(@Part List<MultipartBody.Part> partList);


    /**
     * 查询当前书的所有章节
     *
     * @param write_id  作品id
     * @param sectionid 章节id
     * @param page      显示第几页
     * @param rows      每页显示几条数据
     * @param user_id   登陆用户id
     * @return
     */
    @GET("write/first_page.do")
    Observable<ChaptersWrapper> getChapters(@Query("write_id") String write_id, @Query("sectionid") String sectionid, @Query("page") String page, @Query("rows") String rows, @Query("user_id") String user_id);


    /**
     * 查询我参与的/我发起的/我原创的作品
     *
     * @param page   显示第几页
     * @param rows   每页显示几条数据
     * @param userId 登陆用户id
     * @return
     */
    @GET("write/myWrites.do")
    Observable<BasicResponse<List<OpusBean>>> getMyOpus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows, @Query("type") int type);

    /**
     * 查看收藏接口
     *
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    @GET("section/viewKeepWrite.do")
    Observable<BasicResponse<List<OpusBean>>> getCollect(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 章节点赞
     *
     * @return
     */
    @FormUrlEncoded
    @POST("section/updateLikeCount.do")
    Observable<BasicResponse> lightChapter(@FieldMap Map<String, String> map);


    /**
     * 查询我的粉丝
     *
     * @param page   显示第几页
     * @param rows   每页显示几条数据
     * @param userId 登陆用户id
     * @return
     */
    @GET("watch/watchMeUsers.do")
    Observable<BasicResponse<List<FansBean>>> getMyFollows(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);


    /**
     * 获取作品章节内容
     *
     * @param params 参数集合
     * @return
     */
    @GET("section/viewUpSection.do")
    Observable<BasicResponse<List<ArticleBean>>> getArticle(@QueryMap Map<String, String> params);


    /**
     * 添加章节内容
     *
     * @param chapterMap 章节内容数据
     * @return
     */
    @FormUrlEncoded
    @POST("section/add.do")
    Observable<BasicResponse<String>> addChapter(@FieldMap Map<String, String> chapterMap);

    /**
     * 添加关注
     *
     * @param userId  用户id
     * @param focusId 关注用户的id
     * @param type    0.取消关注 1.添加关注
     * @return
     */
    @POST("watch/followUser.do")
    Observable<BasicResponse<String>> addFocus(@Query("user_id") String userId, @Query("watch_user_id") String focusId, @Query("type") int type);


    /**
     * 添加收藏
     *
     * @param chapterMap
     * @return
     */
    @FormUrlEncoded
    @POST("section/motify.do")
    Observable<BasicResponse> addCollect(@FieldMap Map<String, String> chapterMap);

    @GET("banner/viewBanner.do")
    Observable<BasicResponse<List<BannerBean>>> getBanner(@Query("page") String page, @Query("rows") String row);

    @FormUrlEncoded
    @POST("section/updateSection.do")
    Observable<BasicResponse> updateChapter(@FieldMap Map<String, String> chapterMap);
}
