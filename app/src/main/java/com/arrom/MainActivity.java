package com.arrom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arrom.base.BaseActivity;
import com.arrom.base.RxBase;
import com.arrom.download.DownloadCallback;
import com.arrom.download.DownloadFactory;
import com.arrom.presenter.LoginPresenter;

import java.io.File;

public class MainActivity extends BaseActivity<LoginPresenter,RxBase> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter.login("手机号码","123456");
    }

    /***
     * 下载文件
     * @param url 下载的url
     */
    private void download(String url) {
        DownloadFactory.getInstance().createRxDownload()
                .downloadBigFile(url)
                .enqueue(new DownloadCallback("路径","文件名") {
                    @Override
                    public void progress(long progress, long total, boolean isDone) {
                        //下载进度的处理


                    }
                    @Override
                    public void success() {
                       //下载成功的处理
                    }

                    @Override
                    public void failure() {
                        //失败的处理
                    }
                });
    }
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void setNetDataSuccess(RxBase rxBase) {
      //返回成功之后的处理
    }
}
