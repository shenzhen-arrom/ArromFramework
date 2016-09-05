package com.arrom.base;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}()
 * @Date 16/9/5 下午9:28
 * @Version V2.0
 */
public interface BaseView<T> {

    void showLoading();//显示加载框
    void hideLoading();//隐藏加载框
    void setNetDataFail(String message);//网络请求失败
    void setNetDataSuccess(T t);
}
