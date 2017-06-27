package com.yqy.mvp_frame.frame.utils;

import android.content.SharedPreferences;

import com.yqy.mvp_frame.App;

import static android.content.Context.MODE_PRIVATE;

/**
 *
 * Created by yanqy on 2017/4/18.
 */

public class SPUtil {
    private SharedPreferences sp;
    private static SPUtil instance = null;
    public static String SP_NAME = "MVP_Frame";

    public static SPUtil getInstance() {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null)
                    instance = new SPUtil();
            }
        }
        return instance;
    }

    private SPUtil(){
        sp = App.getInstance().getSharedPreferences(SP_NAME,MODE_PRIVATE);
    }

    public void write(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void write(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void write(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public int read(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public boolean read(String key, Boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public String read(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }
}
