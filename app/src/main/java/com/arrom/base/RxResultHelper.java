package com.arrom.base;

import com.arrom.exception.ArromException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(服务器返回数据的封装)
 * @Date 16/9/5 下午9:14
 * @Version V2.0
 */
public class RxResultHelper {
    public static <T> Observable.Transformer<BaseResult<T>, T> handleResult() {
        return new Observable.Transformer<BaseResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResult<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(BaseResult<T> result) {
                                if (result.code == 0) {
                                    return createData(result.data);
                                } else if (result.code == 100) {
                                    //两个用户同时登录
                                } else {
                                    if(result!=null){
                                        return Observable.error(new ArromException(result.message));
                                    }
                                }
                                return Observable.empty();
                            }
                        }

                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if(!subscriber.isUnsubscribed()){//如果订阅了
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
