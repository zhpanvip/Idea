package com.cypoem.idea.net;

import android.text.TextUtils;
import android.util.Log;

import com.cypoem.idea.app.MainApplication;
import com.cypoem.idea.security.aes.AESUtil;
import com.cypoem.idea.security.rsa.RSAKeyProvider;
import com.cypoem.idea.security.rsa.RSAUtils;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/6 10:13
* Description:对表单提交的数据进行aes加密
*/
public class AESInterceptor implements Interceptor {
    public String key = "123456789aaaaaaa";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            Request newRequest = null;
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                FormBody.Builder formBuilder = new FormBody.Builder();
                String keyMI = null;
                for (int i = 0; i < formBody.size(); i++) {
                    if (formBody.name(i).equals("param")) {
                        String json = AESUtil.encrypt(formBody.value(i), key);
                        if (!TextUtils.isEmpty(json)) {
                            formBuilder.add("data", json);
                            RSAPublicKey pk = RSAKeyProvider.loadPublicKeyByStr(MainApplication.getPublicKeyStore());
                            keyMI = RSAUtils.encryptByPublicKey(key,pk);
                            formBuilder.add("key",keyMI);
                        }
                    }else{
                        formBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                FormBody newFormBody = formBuilder.build();
                Request.Builder builder = request.newBuilder();
                Log.i("wang","keyMI:"+keyMI);
                if(keyMI!=null){
                    builder.addHeader("key",keyMI);
                }
                newRequest = builder
                        .method(request.method(), newFormBody)
                        .removeHeader("Content-Length")
                        .addHeader("Content-Length", newFormBody.contentLength() + "")
                        .build();
            }
            Response response = chain.proceed(newRequest == null ? request : newRequest);
            String result = response.body().string();
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), result)).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return chain.proceed(request);
    }
}
