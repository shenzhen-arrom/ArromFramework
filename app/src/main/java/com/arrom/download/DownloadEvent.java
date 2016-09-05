package com.arrom.download;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.download
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/6 下午10:02
 * @Version V2.0
 */
public class DownloadEvent {
    public long bytesRead;//当前读取的长度
    public long contentLength;//总长度
    public boolean done;//时候读取完成
    public DownloadEvent(){}
    public DownloadEvent(long bytesRead,long contentLength,boolean done){
        this.bytesRead=bytesRead;
        this.contentLength=contentLength;
        this.done=done;
    }
}
