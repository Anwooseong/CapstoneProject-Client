package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.login.LoginResult;

public interface LoginView {

    /**
     * 로그인 API 성공했을 때 호출
     *
     * @param code   응답 코드
     * @param result 로그인 response의 result
     */
    void onLoginSuccess(int code, LoginResult result);

    /**
     * 로그인 API 실패했을 때 호출
     */
    void onLoginFailure();
}
