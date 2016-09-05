package com.arrom.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arrom.presenter.BasePresenter;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @Date 16/9/5 下午9:48
 * @Version V2.0
 */
public abstract  class BaseActivity<P extends BasePresenter,V> extends AppCompatActivity implements BaseView<V>{

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter=createPresenter();
        super.onCreate(savedInstanceState);
    }
    protected  abstract P createPresenter();
    @Override
    public void showLoading() {
        //显示进度框
    }

    @Override
    public void hideLoading() {
        //进度框消失
    }

    @Override
    public void setNetDataFail(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
