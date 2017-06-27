package com.yqy.mvp_frame.ui.login;

import com.yqy.mvp_frame.frame.BaseView;

/**
 * Created by DerekYan on 2017/6/27.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter>{
        void setTitle(String title);
    }

    interface Presenter{
        void login(String username,String pwd);
    }


}
