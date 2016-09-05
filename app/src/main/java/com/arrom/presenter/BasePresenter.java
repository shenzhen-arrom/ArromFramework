package com.arrom.presenter;

import rx.subscriptions.CompositeSubscription;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.presenter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/5 下午9:32
 * @Version V2.0
 */
public class BasePresenter<V> implements Presenter<V>{

    public V view;

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
        onUnsubscribe();
    }

    private void onUnsubscribe() {
        if(mCompositeSubscription!=null&&mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }
}
