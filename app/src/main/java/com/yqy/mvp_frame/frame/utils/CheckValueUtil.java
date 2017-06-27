package com.yqy.mvp_frame.frame.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by DerekYan on 2017/4/19.
 */

public class CheckValueUtil {

    /**
     * 支付密码
     * @param password
     * @return
     */
    public static String checkPayPwdValue(String password){
        String tip = "";
        Pattern p = Pattern.compile("^.*(.)\\1{3}.*$");
        if (TextUtils.isEmpty(password)) {
            tip = "密码不能为空";
        } else if (password.length() != 6) {
            tip = "支付密码长度应为6";
        } else if (p.matcher(password).matches()) {
            tip = "支付密码不得包含4位连续相同字符";
        }
        return  tip;
    }

    /**
     * 检验密码
     * @param password
     * @return
     */
    public static String checkPasswordValue(String password) {
        String tip = "";
        Pattern p = Pattern.compile("^.*(.)\\1{3}.*$");
        if (TextUtils.isEmpty(password)) {
            tip = "密码不能为空";
        } else if (password.length() < 6) {
            tip = "密码不少于6位字符，区分大小写";
        } else if (p.matcher(password).matches()) {
            tip = "密码不得包含4位连续相同字符";
        }
        return tip;
    }

    /**
     * 检验手机号码
     * @param str
     * @return
     */
    public static String checkPhoneValue(String str) {
        String tip = "";
        if (TextUtils.isEmpty(str)) {
            tip = "请输入手机号码";
        } else if (str.length() != 11
                || str.charAt(0) != '1') {
            tip = "请输入正确的手机号码";
        } else if (str.startsWith("110")) {
            return tip;
        } else if (!ModelUtils.isMobileNO(str)) {
            // tip = "手机号码格式不正确";
        } else if (str.length() == 11) {
        }
        return tip;
    }
}
