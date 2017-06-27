package com.yqy.mvp_frame.frame;

/**
 * Created by DerekYan on 2017/6/27.
 */

public interface BaseView<T>{

    void setPresenter(T presenter);

    boolean isActive();

}
