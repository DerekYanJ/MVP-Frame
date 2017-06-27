package com.yqy.mvp_frame.frame;

import android.content.Intent;

import com.yqy.mvp_frame.App;
import com.yqy.mvp_frame.ui.login.LoginActivity;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class BasePresenterImpl<M extends BaseModel,V extends BaseView> implements BasePresenter<M,V> {
    protected V mView;
    protected M mModel;

    @Override
    public void start() {

    }

    @Override
    public void setModel(M model) {
        mModel = model;
        model.setListener(this);
    }

    @Override
    public void setView(V view) {
        mView = view;
    }

    @Override
    public void onNext(Object o, int requestId) {

    }

    @Override
    public void onError(int errorCode, String msg, int requestId) {
        if(msg.indexOf("session") != -1 || requestId == 1001){
            Intent intent = new Intent(App.getInstance(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            App.getInstance().startActivity(intent);
        }
        else if(msg.indexOf("com.i19e.store.http.HttpRequest") != -1) {
            App.getInstance().showToast("请求超时");
        }else if(msg.indexOf("HTTP 500 Internal Server Error") != -1){
            App.getInstance().showToast("请求异常");
        }else  App.getInstance().showToast(msg);
    }

    @Override
    public void onStart(int requestId) {

    }

    @Override
    public void onCompleted(int requestId) {

    }
}
