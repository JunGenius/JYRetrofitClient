package com.qj.client.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * des BaseInterceptor
 * author qujun
 * time 2019/2/13 22:52
 * Because had because, so had so, since has become since, why say why。
 **/

public class BaseInterceptor implements Interceptor{
    private Map<String, String> headers;
    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());

    }
}