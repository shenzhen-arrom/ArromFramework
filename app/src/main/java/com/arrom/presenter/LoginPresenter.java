package com.arrom.presenter;

import com.arrom.api.UserAPI;
import com.arrom.base.BaseView;
import com.arrom.base.RxBase;
import com.arrom.base.RxResultHelper;
import com.arrom.base.RxSubscriber;
import com.arrom.model.LoginBean;
import com.arrom.protocol.ArromProtocol;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.presenter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/5 下午9:42
 * @Version V2.0
 */
public class LoginPresenter extends BasePresenter<BaseView<RxBase>> {

    public LoginPresenter(BaseView baseView){
        attachView(baseView);
    }
    public void login(String mobile,String password){
        view.showLoading();
        ArromProtocol.enqueue(UserAPI.class).login(mobile,password)
                .compose(RxResultHelper.<LoginBean>handleResult())
                .subscribe(new RxSubscriber<LoginBean>() {
                    @Override
                    public void rx_Next(LoginBean loginBean) {
                        view.hideLoading();
                        view.setNetDataSuccess(loginBean);
                    }

                    @Override
                    public void rx_Error(String message) {
                        view.hideLoading();
                       view.setNetDataFail(message);
                    }
                });
    }
}
