package com.arrom.download;

import com.arrom.base.RxBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.download
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/6 下午10:04
 * @Version V2.0
 */
public abstract class DownloadCallback implements Callback<ResponseBody> {
    //订阅下载进度
    private CompositeSubscription rxSubscriptions=new CompositeSubscription();
    // 目标文件存储的文件夹路径
    private String destFileDir;
    // 目标文件存储的文件名
    private String destFileName;

    public DownloadCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        //如果文件过大的话,会有问题,所以特地开线程处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    saveFile(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 保存
     *
     * @param response
     * @return
     * @throws IOException
     */
    public File saveFile(Response<ResponseBody> response) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if(file.exists()){
                file.delete();
            }

            byte[] buf = new byte[1024];
            fos = new FileOutputStream(file);
            int len;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            onSuccess();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 订阅文件下载进度
     */
    private void subscribeLoadProgress() {
        rxSubscriptions.add(RxBus.getInstance()
                .toObservable(DownloadEvent.class)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DownloadEvent>() {
                    @Override
                    public void call(DownloadEvent fileLoadEvent) {

                        onProgress(fileLoadEvent.bytesRead, fileLoadEvent.contentLength, fileLoadEvent.done);
                    }
                }));
    }

    public void onProgress(long progress, long total, boolean isDone){
        progress(progress, total, isDone);
    }

    public void onSuccess() {
        unsubscribe();
        success();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        unsubscribe();
        call.cancel();
        failure();
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unsubscribe() {
        if (!rxSubscriptions.isUnsubscribed()) {
            rxSubscriptions.unsubscribe();
        }
    }

    public abstract void progress(long progress, long total, boolean isDone);

    public abstract void success();

    public abstract void failure();
}
