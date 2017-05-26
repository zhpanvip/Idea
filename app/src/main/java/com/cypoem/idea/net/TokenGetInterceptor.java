package com.cypoem.idea.net;

import android.accounts.Account;

import com.cypoem.idea.utils.AccountHelper;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by edianzu on 2017/5/25.
 * 拦截服务器返回的token并进行保存,并且在发起请求的时候自动为头部添加token
 */


public class TokenGetInterceptor  implements Interceptor {
    private Map<String,String> headers = null;
    public TokenGetInterceptor(Map<String,String> headers){
        this.headers = headers;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request newRequest;
        if (headers!=null || !AccountHelper.isShortCookieEmpty()) {
            Request.Builder builder = chain.request().newBuilder();
            if(headers!=null){
                for(Map.Entry<String,String> item : headers.entrySet()){
                    //添加一些其他头部信息，例如appid，userid等，由外部传入
                    builder.addHeader(item.getKey(),item.getValue());
                }
            }
            if (!AccountHelper.isShortCookieEmpty()) {
                builder.addHeader("token", AccountHelper.getShortCookie());
            }
            newRequest = builder.build();
        } else {
            newRequest = chain.request().newBuilder()
                    .build();
        }
        Response response = chain.proceed(newRequest);
        if (response.header("token") != null) {
            //发现短token，保存到本地
            AccountHelper.updateSCookie(response.header("token"));
        }
        String long_token = response.header("long_token");
        if (long_token != null) {
            //发现长token，保存到本地
            AccountHelper.updateLCookie(long_token);
        }
        return response;
    }
}
