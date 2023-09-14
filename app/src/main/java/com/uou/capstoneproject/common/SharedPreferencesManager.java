package com.uou.capstoneproject.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesManager {

    private static final String PREFERENCES_NAME = "autoLogin";

    public static SharedPreferences getPreferences(Context mContext){
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * SharedPreferences에서 저장된 모든 데이터를 삭제하는 메서드입니다.
     * 비동기 방식으로 데이터를 삭제합니다. 변경 사항은 즉시 적용되지 않을 수 있습니다.
     *
     * @param context 컨텍스트 객체
     */
    //apply -> 비동기, commit -> 동기
    public static void clearPreferences(Context context){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


    /**
     * 로그인 정보를 SharedPreferences에 저장하는 메서드입니다.
     *
     * @param context 컨텍스트 객체
     * @param jwt     저장할 JWT 토큰
     */
    public static void setLoginInfo(Context context, String jwt){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("jwt",jwt);

        editor.apply();
    }

    /**
     * SharedPreferences에서 저장된 로그인 정보(JWT 토큰)를 확인하는 메서드입니다.
     *
     * @param context 컨텍스트 객체
     * @return 저장된 JWT 토큰을 포함한 Map 객체
     */
    //jwt 확인
    public static Map<String, String> getLoginJwtInfo(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> LoginInfo = new HashMap<>();
        //nullPointException 막기 위함
        String jwt = prefs.getString("jwt","");
        LoginInfo.put("jwt",jwt);

        return LoginInfo;
    }
}
