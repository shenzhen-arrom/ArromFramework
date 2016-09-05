package com.arrom.base;

import com.arrom.exception.ArromException;

import rx.Subscriber;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(自定义订阅者)
 * @Date 16/9/5 下午9:21
 * @Version V2.0
 */
public  abstract  class RxSubscriber<T> extends Subscriber<T>{


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        //判断网络是否可用(代码自己写)
        if(e instanceof ArromException){
            rx_Error(e.getMessage());
        }else{
            rx_Error("请求失败,请稍后重试!");
        }
    }

    @Override
    public void onNext(T t) {
        rx_Next(t);
    }
    public abstract void rx_Next(T t);
    public abstract void rx_Error(String message);
}
