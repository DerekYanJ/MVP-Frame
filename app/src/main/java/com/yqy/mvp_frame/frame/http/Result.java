package com.yqy.mvp_frame.frame.http;

/**
 * @author derekyan
 * @desc
 * @date 2016/12/6
 */

public class Result<T> {
    public int status = 0 ;
    public String desc = "";
    public T data = null; //返回数据 可对象 可数组
}
