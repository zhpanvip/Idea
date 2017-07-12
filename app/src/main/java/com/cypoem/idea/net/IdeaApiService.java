package com.cypoem.idea.net;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.EverydayReBackBean;
import com.cypoem.idea.module.bean.FansBean;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.post_bean.EverydaySayPost;
import com.cypoem.idea.module.post_bean.PostOpus;
import com.cypoem.idea.module.post_bean.UpdateUserInfo;
import com.cypoem.idea.module.wrapper.ArticleWrapper;
import com.cypoem.idea.module.wrapper.ChaptersWrapper;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by zhpan on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    String HOST = "http://hansanshao.cn:8080/";
    String API_SERVER_URL = HOST + "cys/";


    @GET("福利/10/1")
    Observable<MeiziWrapper> getMeizi();

    /**
     * 注册接口
     * @param partList 注册信息
     * @return
     */
    @Headers("Accept-Encoding: gzip")
    @Multipart
    @PUT("user/register.do")
    Observable<BasicResponse<RegisterBean>> register(@Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("user/register.do")
    Observable<BasicResponse<RegisterBean>> register(@Part("phone") RequestBody phone,@Part("password") RequestBody password,@Part MultipartBody.Part image);

    /**
     * 完善用户信息
     * @param userInfo 用户信息
     * @return
     */
    @POST("user/update.do")
    Observable<BasicResponse> updateUserInfo(@Body UpdateUserInfo userInfo);

    /**
     * 登陆
     * @param mapLogin 登陆信息
     * @return
     */
    @FormUrlEncoded
    @POST("user/login.do")
    Observable<BasicResponse<UserBean>> login(@FieldMap Map<String,Object> mapLogin);

    /**
     * 意见反馈
     * @param adviceMap 意见信息
     * @return
     */
    @FormUrlEncoded
    @POST("advice/add.do")
    Observable<BasicResponse<String>> postAdvice(@FieldMap Map<String,String> adviceMap);

    /**
     * 根据用户id获取用户信息
     * @param user_id
     * @return
     */
    @GET("user/viewUser.do")
    Observable<BasicResponse<UserBean>> getUserInfo(@Query("user_id") String user_id);

    /**
     * 每日一句
     * @param everydaySay
     * @return
     */
    @POST("everySay/add.do")
    Observable<BasicResponse> publishEverydaySay(@Body EverydaySayPost everydaySay);

    /**
     * 每日一句往期回看
     * @param page 当前页数
     * @param number 每页显示的条目
     * @return
     */
    @GET("everySay/selectAll.do")
    Observable<BasicResponse<EverydayReBackBean>> lookBack(@Query("page") int page, @Query("rows") int number);

    /**
     * 首页数据
     * @param page
     * @param number
     * @return
     */
    @Headers("Cache-Control: public, max-age=600")
    @GET("write/first_page.do")
    Observable<BasicResponse<List<HomePageBean>>> getHomePageData(@Query("page") int page, @Query("rows") int number);

    /**
     * 发布作品
     * @param partList
     * @return
     */
    @POST("/write/add.do")
    Observable<BasicResponse<String>> publishOpus(@Part List<MultipartBody.Part> partList);

    /**
     * 查询当前书的所有章节
     * @param write_id 作品id
     * @param sectionid 章节id
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param user_id   登陆用户id
     * @return
     */
    @GET("write/first_page.do")
    Observable<ChaptersWrapper> getChapters(@Query("write_id") String write_id, @Query("sectionid") String sectionid, @Query("page") String page, @Query("rows") String rows, @Query("user_id") String user_id);

    /**
     * 查询我发起的作品
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param userId   登陆用户id
     * @return
     */
    @GET("write/myStart.do")
    Observable<BasicResponse<List<OpusBean>>> getMyStartOpus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 查询我原创的作品
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param userId   登陆用户id
     * @return
     */
    @GET("write/myCreate.do")
    Observable<BasicResponse<List<OpusBean>>> getMyOwnOpus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 查询我参与的作品
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param userId   登陆用户id
     * @return
     */
    @GET("write/myJoin.do")
    Observable<BasicResponse<List<OpusBean>>> getMyJoinOpus(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 查询我参与的作品
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param userId   登陆用户id
     * @return
     */
    @GET("watch/watchMeUsers.do")
    Observable<BasicResponse<List<FansBean>>> getMyFans(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 获取作品章节内容
     * @param page  显示第几页
     * @param rows  每页显示几条数据
     * @param userId   登陆用户id
     * @return
     */
    @GET("section/view.do")
    Observable<ArticleWrapper> getArticle(@Query("user_id") String userId, @Query("page") int page, @Query("rows") int rows, @Query("write_id") String writeId, @Query("sectionid") String sectionId);
}
