package com.arrom.download;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.download
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/6 下午10:03
 * @Version V2.0
 */
public class DownloadFactory {
    private static DownloadFactory mFactory;

    private DownloadFactory(){}

    public static DownloadFactory getInstance(){
        if(mFactory==null){
            synchronized (DownloadFactory.class){
                if(mFactory==null){
                    mFactory=new DownloadFactory();
                }
            }
        }
        return mFactory;
    }
    public DownloadService createRxDownload(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("url")
                .client(getOkHttpClient())
                .build();
        return  retrofit.create(DownloadService.class);
    }
    private OkHttpClient getOkHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new DownloadResponseBody(originalResponse))
                                .build();
                    }
                })
                .build();

        return okHttpClient;
    }
}
