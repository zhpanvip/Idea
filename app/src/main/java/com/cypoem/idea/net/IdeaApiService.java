package com.cypoem.idea.net;

import com.cypoem.idea.module.wrapper.MeiziWrapper;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhpan on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    String HOST = "http://gank.io/";
    String API_SERVER_URL = HOST + "api/data/";

    @GET("福利/10/1")
    Observable<MeiziWrapper> getMeizi();

}
