package com.cypoem.idea.net;


import com.cypoem.idea.module.wrapper.DataWrapper;
import com.cypoem.idea.module.wrapper.MeiziWrapper;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhpan on 2017/4/1.
 */

public interface IdeaApiService {
    //http://192.168.155.5:8080/springMvc/student.do?method=json
    String HOST = "http://gank.io/";
    String API_SERVER_URL = HOST + "api/data/福利/";
    String URL_COMMENT_LIST = "10/1";

    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;


    @GET(URL_COMMENT_LIST)
    Observable<MeiziWrapper> getMeizi();

}
