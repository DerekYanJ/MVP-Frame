package com.yqy.mvp_frame.frame;

import com.yqy.mvp_frame.frame.http.SubscriberResultListener;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class BaseModel {
    protected SubscriberResultListener listener;

    public void setListener(SubscriberResultListener listener) {
        this.listener = listener;
    }
}
