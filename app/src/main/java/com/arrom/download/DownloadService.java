package com.arrom.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.download
 * @Description: ${TODO}(下载文件)
 * @Date 16/9/6 下午9:58
 * @Version V2.0
 */
public interface DownloadService {
    /***
     * 下载大文件
     * @param url
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadBigFile(@Url String url);

    /****
     * 下载文件
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> download(@Url String url);
}
