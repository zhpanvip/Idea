package com.cypoem.idea.net;


import com.cypoem.idea.module.wrapper.DataWrapper;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhpan on 2017/4/1.
 */

public interface IdeaApiService {
    //http://192.168.155.5:8080/springMvc/student.do?method=json
    String HOST = "http://192.168.155.5:8080/";
    String API_SERVER_URL = HOST + "springMvc/";
    String URL_COMMENT_LIST = "student.do";
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 10000;

    @GET(URL_COMMENT_LIST)
    Observable<DataWrapper> getData(@Query("method") String method);


}
