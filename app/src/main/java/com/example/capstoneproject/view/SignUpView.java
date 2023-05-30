package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.signup.SignUpResponse;

public interface SignUpView {

    /**
     * 회원 가입 API 성공했을 때 호출
     */
    void onSignUpSuccess();

    /**
     * 회원 가입 API 실패했을 때 호출
     *
     * @param response 회원 가입 실패 시 받은 SignUpResponse 객체
     */
    void onSignUpFailure(SignUpResponse response);
}
