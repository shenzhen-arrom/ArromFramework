package com.arrom.api;

import com.arrom.base.BaseResult;
import com.arrom.model.LoginBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.api
 * @Description: ${TODO}(与用户相关的接口)
 * @Date 16/9/5 下午9:38
 * @Version V2.0
 */
public interface UserAPI {
    //login
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResult<LoginBean>> login(@Field("userInfo.mobile") String mobile, @Field("userInfo.passWord") String pwd);


}
