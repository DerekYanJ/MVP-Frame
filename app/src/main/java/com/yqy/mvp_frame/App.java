package com.yqy.mvp_frame;

import android.app.Application;
import android.widget.Toast;

import com.blankj.utilcode.utils.AppUtils;

/**
 * @author derekyan
 * @desc
 * @date 2016/12/6
 */

public class App extends Application {
    public static String VERSIONNAME = "";

    public static App mApp;
    public static App getInstance(){
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        VERSIONNAME = AppUtils.getAppVersionName(this);
    }

    /**
     * Toast 吐丝
     * @param tip 提示文字
     */
    public void showToast(String tip){
        Toast.makeText(this, tip , Toast.LENGTH_SHORT).show();
    }
}
