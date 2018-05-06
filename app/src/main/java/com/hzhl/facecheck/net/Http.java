/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 37636 on 2018/1/10.
 */

public class Http {

    public static Links links;
    //public static String BASE_URL = "http://121.46.4.23:8080";
     public static String BASE_URL = "http://www.3huanju.com";
    // public static String BASE_URL = "http://192.168.1.14:8080";
    public static Http http;
    //static Context context;

    public static Http getIntens() {
        http = new Http();
        return http;
    }

    private Http() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .build();
        links = retrofit.create(Links.class);
    }

    public void call(final Context context, Call<ResponseBody> responseBodyCall,  final JsonCallback jsonCallback) {
        
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
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void call(Call<ResponseBody> responseBodyCall,  final JsonCallback jsonCallback) {

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

    public interface JsonCallback {
        void onResult(String json, String error);

        void onFail(String error);

    }

    public void getHttp(String url, boolean isShow, final JsonCallback jsonCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                jsonCallback.onFail(e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String data = response.body().string();
                jsonCallback.onResult(data, "");
            }
        });
    }
}
