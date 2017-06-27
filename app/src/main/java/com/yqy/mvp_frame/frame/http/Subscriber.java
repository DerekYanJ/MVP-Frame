package com.yqy.mvp_frame.frame.http;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class Subscriber<T> extends rx.Subscriber<T> {
    private SubscriberResultListener mSubscriberResultListener;
    private int requestId; //请求ID 用于识别接口

    public Subscriber(SubscriberResultListener subscriberResultListener, int requestId) {
        mSubscriberResultListener = subscriberResultListener;
        this.requestId = requestId;
    }

    /**
     * 请求网络开始前
     */
    @Override
    public void onStart() {

    }

    /**
     * 请求网络结束后
     */
    @Override
    public void onCompleted() {

    }

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法
     *
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * 对返回数据进行操作的回调， UI线程
     *
     * @param t 返回数据bean
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberResultListener != null)
            mSubscriberResultListener.onNext(t, requestId);
    }

    /**
     * 关闭请求
     */
    public void cancelRequest(){
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
