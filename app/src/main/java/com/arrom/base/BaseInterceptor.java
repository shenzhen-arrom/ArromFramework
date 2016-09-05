package com.arrom.base;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(拦截器)
 * @Date 16/9/5 下午9:08
 * @Version V2.0
 */
public class BaseInterceptor implements Interceptor{
    private Map<String,String> headers;

    public BaseInterceptor(Map<String,String> headers){
        this.headers=headers;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder=chain.request().newBuilder();
        if(headers!=null&&headers.size()>0){
            Set<String> keys=headers.keySet();
            for (String headerKey:keys) {
                builder.addHeader(headerKey,headers.get(headerKey)).build();
            }
        }
        Response response=chain.proceed(builder.build());
        return response;
    }
}
