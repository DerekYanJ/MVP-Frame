package com.yqy.mvp_frame.ui.login;

import com.yqy.mvp_frame.frame.BaseModel;
import com.yqy.mvp_frame.frame.http.HttpRequest;
import com.yqy.mvp_frame.frame.http.Subscriber;
import com.yqy.mvp_frame.frame.utils.SPUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class LoginModel extends BaseModel {
    public static int LOGIN = 1;

    /**
     * 登录
     * @param username
     * @param pwd
     * @param requestId
     */
    public void login(String username,String pwd,int requestId){
        Map<String,String> params = new HashMap<>();
        params.put("interfacecode","doLogin");
        params.put("username",username);
        params.put("password",pwd);
        HttpRequest.getInstance().getSessionnucheck(new Subscriber<JSONObject>(listener,requestId),params);
    }

    public void saveLoginInfo(LoginBean bean){
        SPUtil sp = SPUtil.getInstance();
        sp.write("address",bean.address + "");
        sp.write("cash_id",bean.cash_id + "");
        sp.write("company_name",bean.company_name + "");
        sp.write("id_card",bean.id_card + "");
        sp.write("provice_id",bean.provice_id + "");
        sp.write("sessionid",bean.sessionid + "");
        sp.write("user_id",bean.user_id + "");
        sp.write("user_name",bean.user_name + "");
        sp.write("user_phone",bean.user_phone + "");
        sp.write("user_safe_tel",bean.user_safe_tel + "");
        sp.write("user_safe_tel1",bean.user_safe_tel1 + "");
        sp.write("user_status",bean.user_status + "");
        sp.write("zlat",bean.zlat + "");
        sp.write("zlon",bean.zlon + "");
        sp.write("city_id",bean.city_id + "");
        sp.write("city",bean.city + "");
        sp.write("province",bean.province + "");
        sp.write("province_id",bean.province_id + "");
        sp.write("zone_id",bean.zone_id + "");
        sp.write("zone",bean.zone + "");
        sp.write("e_mail",bean.e_mail + "");
        sp.write("create_time",bean.create_time + "");
        sp.write("agrNumber",bean.agrNumber + "");
    }


}
