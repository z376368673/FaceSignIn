/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.net;

import android.util.Log;

import com.hzhl.facecheck.utils.PreferencesUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APIServices {
    private static volatile APIServices instance;
    private static final String BASE_URL = "http://192.168.1.15:8082/sign/";
    public static volatile  Links links;

    /**
     * 账号
     */
    private static  String identify="";
    private String accessToken;
    private static final String groupId  = "sign_in";

    private APIServices() {}


    public static APIServices getInstance() {
        if (instance == null) {
            synchronized (APIServices.class) {
                if (instance == null) {
                    instance = new APIServices();
                    Retrofit retrofit = new Retrofit
                            .Builder()
                            .baseUrl(BASE_URL)
                            .build();
                    links = retrofit.create(Links.class);
                }
            }
        }
        return instance;
    }

    public String getIdentify() {
        //identify = PreferencesUtil.getString("identify","");
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
       // PreferencesUtil.putString("identify",identify);
    }

    /**
     * 设置accessToken 如何获取 accessToken 详情见:
     *
     * @param accessToken accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }


    /**
     * 登陆
     * @param callback
     * @param file
     */
    public void userLogin(File file,Http.JsonCallback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //.addFormDataPart("group_id",groupId)
                .addFormDataPart("identify", String.valueOf(identify))
                .addFormDataPart("imgPath",file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        List<MultipartBody.Part> parts = builder.build().parts();
        Call call = links.userLogin(parts);
        httpLink(call,callback);
    }




    /**
     * 发起请求
     * @param responseBodyCall
     * @param jsonCallback
     */
    public void httpLink(Call<ResponseBody> responseBodyCall,  final Http.JsonCallback jsonCallback) {
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() == null) {
                        Log.e("url", response.raw().toString());
                        jsonCallback.onFail("获取数据失败！" + response.message());
                    } else {
                        String data = response.body().string();
                        Log.e("data", data);
                        Log.e("url", response.raw().toString());
                        jsonCallback.onResult(data, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String msg = e.getMessage() == null ? "数据解析失败" : e.getMessage();
                    jsonCallback.onFail(msg);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                String msg = throwable.getMessage() == null ? "获取数据失败" : throwable.getMessage();
                Log.e("error", msg);
                jsonCallback.onFail(msg);
            }
        });
    }

    /**
     * 请求回调接口
     */
    public interface JsonCallback {
        void onResult(String json, String error);

        void onFail(String error);

    }
}
