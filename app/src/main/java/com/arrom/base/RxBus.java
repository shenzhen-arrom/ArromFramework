package com.arrom.base;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/6 下午10:00
 * @Version V2.0
 */
public class RxBus {
    private static volatile RxBus mInstance;

    private final Subject rxBus;

    private RxBus(){
        rxBus=new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance(){
        RxBus rxBus=mInstance;
        if(rxBus==null){
            synchronized (RxBus.class){
                rxBus=mInstance;
                if(rxBus==null){
                    rxBus=new RxBus();
                    mInstance=rxBus;
                }
            }
        }
        return rxBus;
    }
    //send event
    public void send(Object event){
        rxBus.onNext(event);
    }
    //return observable
    public <T> Observable<T> toObservable(Class<T> eventType){
        return rxBus.ofType(eventType);
    }
    //
    public boolean hasObservers(){
        return rxBus.hasObservers();
    }
}
