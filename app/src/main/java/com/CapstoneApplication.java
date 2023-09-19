package com;

import android.app.Application;
import android.content.Context;

import com.kakao.sdk.common.KakaoSdk;
import com.uou.capstoneproject.R;

public class CapstoneApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        KakaoSdk.init(this, getString(R.string.kakao_app_key));
    }

    public static Context getAppContext() {
        return appContext;
    }
}
