package com.yqy.mvp_frame.frame.http;

import com.blankj.utilcode.utils.DeviceUtils;
import com.yqy.mvp_frame.App;
import com.yqy.mvp_frame.BuildConfig;
import com.yqy.mvp_frame.R;
import com.yqy.mvp_frame.frame.utils.DESUtils;
import com.yqy.mvp_frame.frame.utils.L;
import com.yqy.mvp_frame.frame.utils.MakeHfRequestUrlUtil;
import com.yqy.mvp_frame.frame.utils.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.lang.String.valueOf;

/**
 * @author derekyan
 * @desc 封装的 RxJava + Retrofit 请求
 * @date 2016/12/6
 */

public class HttpRequest {
    private static final int DEFAULT_TIMEOUT = 30;//超时时间
    private Retrofit mRetrofit;
    private HttpService mHttpService;
    private String desKey = "";

    //普通的获取数据 Object可替换要的类型

    /**
     *
     * @param subscriber
     * @param params
     */

    public void getResult(Subscriber<JSONObject> subscriber, Map<String, String> params) {
        toSubscribe(mHttpService.getResult(getSpecialParams(params)).map(new ResultFunc<JSONObject>()), subscriber);
    }

    public void getSessionnucheck(Subscriber<JSONObject> subscriber, Map<String, String> params) {
        toSubscribe(mHttpService.getSessionnucheck(getSpecialParams(params)).map(new ResultFunc<JSONObject>()), subscriber);
    }

    /**
     * 加密处理之后的map
     * @param params
     * @return
     */
    public Map<String, String> getSpecialParams(Map<String, String> params,String img){
        Map<String,String> newParams = new HashMap<>();
        List<String> mdkList = new ArrayList<>();
        Set<String> set = params.keySet();
        for(Iterator iterator = set.iterator(); iterator.hasNext();)
            mdkList.add((String)iterator.next());
        mdkList = sortListString(mdkList);
        String sign = MakeHfRequestUrlUtil.getMd5(
                desKey, params, mdkList);
        newParams.put("sign", sign);
        params.put("img", img);
        String p = params.toString();
        p = p.substring(1,p.length() - 1);
        p = p.substring(0,p.length());
        String str = p.replace(",","&").replace("~",",").replace("& ","&").trim();
        if (BuildConfig.DEBUG) L.e("requestParams",str);
        String stss = DESUtils.encryption(str,desKey);
        newParams.put("data",stss =stss.replace("+","%2B").replace(" ",""));
        return newParams;
    }

    /**
     * 加密处理之后的map
     * @param params
     * @return
     */
    public Map<String, String> getSpecialParams(Map<String, String> params){
        Map<String,String> newParams = new HashMap<>();
        List<String> mdkList = new ArrayList<>();
        Set<String> set = params.keySet();
        for(Iterator iterator = set.iterator(); iterator.hasNext();)
            mdkList.add((String)iterator.next());
        mdkList = sortListString(mdkList);
        String sign = MakeHfRequestUrlUtil.getMd5(
                desKey, params, mdkList);
        newParams.put("sign", sign);
        String p = params.toString();
        p = p.substring(1,p.length() - 1);
        p = p.substring(0,p.length());
        String str = p.replace(",","&").replace("~",",").replace("& ","&").trim();
        if (BuildConfig.DEBUG) L.e("requestParams",str);
        String stss = DESUtils.encryption(str,desKey);
        newParams.put("data",stss );
        return newParams;
    }

    /**
     * 参数排序
     * @param map
     * @return
     */
    public Map<String, String> sortParams(Map<String, String> map) {
        Map<String, String> newParams = new HashMap<>();
        Object[] objects = map.keySet().toArray();
        Arrays.sort(objects);
        for (Object object : objects) {
            String key = (String) object;
            newParams.put(key, map.get(key));
        }
        return newParams;
    }

    /**
     * list 根据字母排序
     * @param list
     * @return
     */
    public List<String> sortListString(List<String> list) {
        Object[] obj = list.toArray();
        Arrays.sort(obj);
        list.clear();
        for (Object object : obj) {
            list.add((String) object);
        }
        return list;
    }

    /**
     * 在访问HttpService时创建单例
     */
    private static class SingletonHolder {
        private static final HttpRequest INSTANCE = new HttpRequest();
    }

    /**
     * 获取单例
     * 既实现了线程安全，又避免了同步带来的性能影响
     * @return
     */
    public static HttpRequest getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HttpRequest() {
        OkHttpClient.Builder mBuilder = new OkHttpClient().newBuilder();
        mBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //超时时间 单位:秒
        //DEBUG 测试环境添加日志拦截器
        if(L.isShow){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(loggingInterceptor);
        }
        //添加一个设置header拦截器
        mBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //可以设置和添加请求头数据
                Request.Builder builder = chain.request().newBuilder();
                Request mRequest = chain.request().newBuilder()
                        .header("User-Agent","android/" +
                                App.VERSIONNAME + "(" +
                                DeviceUtils.getSDKVersion() + ";" +
                                DeviceUtils.getModel() + ")")
                        .header("Cookie", "SSOID="+ SPUtil.getInstance().read("sessionid",""))
//                            .header("Cookie", "JSESSIONID="+ SPUtil.getInstance().read("sessionid",""))
                        .build();
                try {
                    return chain.proceed(mRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });

        mRetrofit = new Retrofit.Builder()
                .client(mBuilder.build())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(App.getInstance().getResources().getString(R.string.requestUri)) //基地址 可配置到gradle
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
        desKey = App.getInstance().getResources().getString(R.string.DesKey);
    }

    class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody,Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }

    /**
     * 封装切换线程
     *
     * @param o
     * @param s
     * @param <T>
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 请求数据统一进行预处理
     *
     * @param <T>
     */
    private class ResultFunc<T> implements Func1<ResultData<T>, T> {
        @Override
        public T call(ResultData<T> result) {
            Result<T> realResult = new Result<>();
            try {
                if( realResult == null || result == null){
                    throwException("-2","加载失败");
                }
                //主动抛异常  会自动进去OnError方法
                String resultStr = DESUtils.decrypt(result.data.replace(" ",""),App.getInstance().getResources().getString(R.string.DesKey));
                L.e("resultStr",resultStr);
                JSONObject jsonObject = new JSONObject(resultStr);
                realResult.status = Integer.parseInt(jsonObject.getString("status"));
                realResult.desc = jsonObject.getString("desc");
                JSONObject dataJo = null;
                JSONArray dataJa = null;
                try {
                    dataJo = jsonObject.getJSONObject("data");
                    realResult.data = (T) dataJo;
                }catch (JSONException e){
                    try {
                        dataJa = jsonObject.getJSONArray("data");
                        realResult.data = (T) dataJa;
                    } catch (JSONException e1) {
//                        e1.printStackTrace();
                    }
                }
                if (realResult.status == 0) {
//                if (realResult.status == 0 && realResult.data != null) {
                    return realResult.data;
                }else {
                    JSONObject errorJson = new JSONObject();
                    errorJson.put("errorCode", realResult.status + "");
                    errorJson.put("errorMsg", realResult.desc + "");
                    throw new ApiException(errorJson.toString());
                }
            } catch (JSONException e) {
                    e.printStackTrace();
                throwException("-1","加载失败");
            }
            return null;
        }
    }

    private void throwException(String errorCode,String errorMsg){
        try {
            JSONObject errorJson = new JSONObject();
            errorJson.put("errorCode", errorCode);
            errorJson.put("errorMsg",errorMsg);
            throw new ApiException(errorJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            throwException("-1","加载失败");
        }
    }

    /**
     * 上传文件
     * @param params 参数
     * @param file 文件
     * @param mCallback 回调函数
     */
    public void uploadFile(final Map<String, String> params, File file, Callback mCallback) {
        final String url = "baseUrl";
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("uploadedfile", file.getName(), body);
        }
        if (params != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : params.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(mCallback);
    }
}
