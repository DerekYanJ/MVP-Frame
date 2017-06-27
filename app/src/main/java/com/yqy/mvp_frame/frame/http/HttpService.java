package com.yqy.mvp_frame.frame.http;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author derekyan
 * @desc
 * @date 2016/12/6
 */

public interface HttpService {

    /**
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("")
    Observable<ResultData<JSONObject>> getResult(@FieldMap Map<String, String> params);

    /**
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("")
    Observable<ResultData<JSONObject>> getSessionnucheck(@FieldMap Map<String, String> params);


}
