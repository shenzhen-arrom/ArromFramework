package com.arrom.protocol;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.arrom.base.BaseInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.protocol
 * @Description: ${TODO}(协议)
 * @Date 16/9/5 下午9:02
 * @Version V2.0
 */
public class ArromProtocol<T> {

    private static Context mContext;

    public static void init(Context context){
        mContext=mContext;
    }
    private static class ArromProtocolHolder{
        private  final static ArromProtocol INSTANCE=new ArromProtocol();
    }
    public static ArromProtocol getInstance(){
        return ArromProtocolHolder.INSTANCE;
    }

    /***
     * 调用接口
     * @return
     */
    public static <T>T enqueue(Class<T> t){
        return getInstance().initRetrofit().create(t);
    }

    /**
     * 获取retrofit对象
     *
     * @return retrofit对象
     */
    private static Retrofit initRetrofit() {
        OkHttpClient client=genericClient(true);
        return new Retrofit.Builder()
                .baseUrl("基础的URL")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//rx回调
                .build();
    }

    public static OkHttpClient genericClient(boolean debug) {
        HttpLoggingInterceptor logging=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.e("ArromProtocol",message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=null;
        if(debug){
            client=new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new BaseInterceptor(addHeader())).addInterceptor(logging)
                    .writeTimeout(10,TimeUnit.SECONDS).build();
        }else{
            client=new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new BaseInterceptor(addHeader()))
                    .writeTimeout(10,TimeUnit.SECONDS).build();
        }
        return client;
    }

    private static Map<String,String> addHeader() {
        //自定义一些头部信息
        Map<String,String> headers=new HashMap<>();
        headers.put("version", "1.3.2");
        headers.put("code", "132");
        headers.put("client", "android");
        return headers;
    }
}
