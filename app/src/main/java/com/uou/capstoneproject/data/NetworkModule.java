package com.uou.capstoneproject.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    // 클라이언트가 연결할 서버의 주소
    private final static String BASE_URL = "https://prod.capstoneuou.shop/api/app/";

    public static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}