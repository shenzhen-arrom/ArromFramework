package com.arrom.base;

import android.app.Application;

import com.arrom.protocol.ArromProtocol;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/5 下午9:56
 * @Version V2.0
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ArromProtocol.init(getApplicationContext());
    }
}
