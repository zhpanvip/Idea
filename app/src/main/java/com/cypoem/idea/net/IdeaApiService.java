package com.cypoem.idea.net;

import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.EveryDayReBackBean;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.post_bean.AdvicePost;
import com.cypoem.idea.module.post_bean.EverydaySayPost;
import com.cypoem.idea.module.post_bean.LoginPost;
import com.cypoem.idea.module.post_bean.PostOpus;
import com.cypoem.idea.module.post_bean.RegisterPost;
import com.cypoem.idea.module.post_bean.UpdateUserInfo;
import com.cypoem.idea.module.wrapper.ChaptersWrapper;
import com.cypoem.idea.module.wrapper.HomePageWrapper;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    String API_SERVER_URL = HOST + "/cys/";


    @GET("福利/10/1")
    Observable<MeiziWrapper> getMeizi();

    /**
     * 注册接口
     * @param register 注册信息
     * @return
     */
    @POST("user/register.do")
    Observable<BasicResponse<RegisterBean>> register(@Body RegisterPost register);

    /**
     * 完善用户信息
     * @param userInfo 用户信息
     * @return
     */
    @POST("user/update.do")
    Observable<BasicResponse> updateUserInfo(@Body UpdateUserInfo userInfo);

    /**
     * 登陆
     * @param login 登陆信息
     * @return
     */
    @POST("user/login.do")
    Observable<BasicResponse<UserBean>> login(@Body LoginPost login);

    /**
     * 意见反馈
     * @param advice 意见信息
     * @return
     */
    @POST("advice/add.do")
    Observable<BasicResponse<String>> postAdvice(@Body AdvicePost advice);

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
    Observable<BasicResponse<EveryDayReBackBean>> lookBack(@Query("page") String page, @Query("rows") String number);

    /**
     * 首页数据
     * @param page
     * @param number
     * @return
     */
    @GET("write/first_page.do")
    Observable<HomePageWrapper> getHomePageData(@Query("page") String page, @Query("rows") String number);

    /**
     * 发布作品
     * @param opus
     * @return
     */
    @POST("everySay/add.do")
    Observable<BasicResponse> publishOpus(@Body PostOpus opus);


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

}
