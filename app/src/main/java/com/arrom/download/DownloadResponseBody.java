package com.arrom.download;

import com.arrom.base.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.download
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/6 下午9:59
 * @Version V2.0
 */
public class DownloadResponseBody extends ResponseBody{
    Response responseBody;

    public DownloadResponseBody(Response responseBody){
        this.responseBody=responseBody;
    }

    @Override
    public MediaType contentType() {
        return responseBody.body().contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.body().contentLength();
    }

    @Override
    public BufferedSource source() {

        return  Okio.buffer(source(responseBody.body().source()));
    }
    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead == -1?0:bytesRead;
                RxBus.getInstance().send(new DownloadEvent(totalBytesRead, contentLength(), bytesRead == -1));
                return bytesRead;
            }
        };
    }
}
