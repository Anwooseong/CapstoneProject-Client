package com.example.capstoneproject.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesManager {

    private static final String PREFERENCES_NAME = "my_preferences";

    public static SharedPreferences getPreferences(Context mContext){
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    //apply -> 비동기, commit -> 동기
    public static void clearPreferences(Context context){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static void setLoginInfo(Context context, String jwt){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        /*editor.putString("id", id);
        editor.putString("password", password);*/
        editor.putString("jwt",jwt);

        editor.apply();
    }

    public static Map<String, String> getLoginInfo(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> LoginInfo = new HashMap<>();
        //nullPointException 막기 위함
//        String id = prefs.getString("id", "");
//        String password = prefs.getString("password", "");
//
//        LoginInfo.put("id", id);
//        LoginInfo.put("password", password);
        String jwt = prefs.getString("jwt","");
        Log.d("TAG", "getLoginInfo: "+jwt);
        LoginInfo.put("jwt",jwt);

        return LoginInfo;
    }
}
