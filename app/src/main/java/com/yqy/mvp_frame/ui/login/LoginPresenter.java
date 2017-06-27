package com.yqy.mvp_frame.ui.login;

import com.yqy.mvp_frame.frame.BasePresenterImpl;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class LoginPresenter extends BasePresenterImpl<LoginModel,LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String username, String pwd) {
        mModel.login(username,pwd,LoginModel.LOGIN);
    }

    @Override
    public void onNext(Object o, int requestId) {
        if(requestId == LoginModel.LOGIN){
            mView.setTitle("");
        }
    }
}
