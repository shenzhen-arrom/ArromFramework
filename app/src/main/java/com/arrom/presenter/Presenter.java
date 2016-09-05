package com.arrom.presenter;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.presenter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/5 下午9:27
 * @Version V2.0
 */
public interface Presenter<V> {

    void attachView(V view);//关联

    void detachView();//解除关联
}
