package com.cypoem.idea.net;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.CommentBean;
import com.cypoem.idea.module.bean.EverydayReBackBean;
import com.cypoem.idea.module.bean.FansBean;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.wrapper.ChaptersWrapper;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    int DEFAULT_TIMEOUT = 20000;

    //String HOST = "http://hansanshao.cn:8080/";
    String HOST = "http://cypoem.com:8080/";
    String API_SERVER_URL = HOST + "cys/";


    /**
     * 注册接口
     *
     * @param partList 注册信息
     * @return
     */
    @Multipart
    @POST("user/register.do")
    Observable<BasicResponse<RegisterBean>> register(@Part List<MultipartBody.Part> partList);

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
     * 上传头像/作者照片接口
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
    Observable<BasicResponse<UserBean>> login(@FieldMap Map<String, Object> mapLogin);

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
     * 根据用户id获取用户信息
     *
     * @param user_id   查看的用户id
     * @param login_user_id    当前登录的用户id
     * @return
     */
    @GET("user/viewUser.do")
    Observable<BasicResponse<UserBean>> getUserInfo(@Query("user_id") String user_id,@Query("login_user_id") String login_user_id);

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
     * 首页数据
     *
     * @param page
     * @param number
     * @return
     */
    @Headers("Cache-Control: public, max-age=600")
    @GET("write/first_page.do")
    Observable<BasicResponse<List<HomePageBean>>> getHomePageData(@Query("page") int page, @Query("rows") int number);

    /**
     * 发现页面数据
     *
     * @param page
     * @param number
     * @param type 0最新 1最热
     * @return
     */
    @Headers("Cache-Control: public, max-age=600")
    @GET("write/discover.do")
    Observable<BasicResponse<List<HomePageBean>>> getDiscoverData(@Query("page") int page, @Query("rows") int number,@Query("type") int type);

    /**
     * 发布作品
     *
     * @param partList
     * @return
     */
    @Multipart
    @POST("write/add.do")
    Observable<BasicResponse> publishOpus(@Part List<MultipartBody.Part> partList);

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
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    @GET("section/viewKeepWrite.do")
    Observable<BasicResponse<List<OpusBean>>> getCollect(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 章节点赞
     * @return
     */
    @FormUrlEncoded
    @POST("section/updateLikeCount.do")
    Observable<BasicResponse> lightChapter(@FieldMap Map<String, String> map);

    /**
     * 查询我关注我的
     *
     * @param page   显示第几页
     * @param rows   每页显示几条数据
     * @param userId 登陆用户id
     * @return
     */
    @GET("watch/myWatchUsers.do")
    Observable<BasicResponse<List<FansBean>>> getMyFocus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);


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
     * 获取作品章节内容
     *
     * @param page 显示第几页
     * @param rows 每页显示几条数据
     * @return
     */
    @GET("write/viewByName.do")
    Observable<BasicResponse<List<OpusBean>>> getSearchData(@Query("write_name") String content, @Query("page") int page, @Query("rows") int rows);


    /**
     * 添加章节内容
     *
     * @param chapterMap 章节内容数据
     * @return
     */
    @FormUrlEncoded
    @POST("advice/add.do")
    Observable<BasicResponse<String>> addChapter(@FieldMap Map<String, String> chapterMap);

    /**
     * 添加关注
     *
     * @param userId  用户id
     * @param focusId 关注用户的id
     * @return
     */
    @POST("watch/add.do")
    Observable<BasicResponse<String>> addFocus(@Query("user_id") String userId, @Query("watch_user_id") String focusId);

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
     * 获取章节评论
     * @param params
     * @return
     */
    @GET("comment/viewComment.do")
    Observable<BasicResponse<List<CommentBean>>> getComment(@QueryMap Map<String, String> params);

    /**
     * 评论章节
     * @param userId 用户id
     * @param sectionId 章节id
     * @param content 评论内容
     * @return
     */
    @POST("comment/add.do")
    Observable<BasicResponse<String>> comment(@Query("user_id") String userId, @Query("section_id") String sectionId,@Query("content") String content);

    /**
     * 为章节点赞
     * @param userId 用户id
     * @param comment_id 要点赞的评论的uid
     * @return
     */
    @POST("commentLike/updateLike.do")
    Observable<BasicResponse<String>> praise(@Query("user_id") String userId, @Query("comment_id") String comment_id);


    @POST("user/updatePwd.do")
    Observable<BasicResponse<String>> updatePsw(@Query("phone") String phone,@Query("password") String password);

    /**
     * 验证该手机号是否注册
     * @param phone 验证的手机号
     * @return
     */
    @GET("user/getUser.do")
    Observable<BasicResponse> isRegistered(@Query("phone") String phone);







    @POST("user/ceshi.do")
    Observable<BasicResponse> test();



}
