package com.uou.capstoneproject.data.auth;

import com.uou.capstoneproject.data.auth.request.User;
import com.uou.capstoneproject.data.auth.response.login.LoginResponse;
import com.uou.capstoneproject.data.auth.response.duplicateuid.DuplicateResponse;
import com.uou.capstoneproject.data.auth.response.signup.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OldAuthRetrofitInterface {

    /**
     * 회원가입 retrofit url
     * @param user
     * @return
     */
    @POST("/app/users")
    Call<SignUpResponse> signUp(@Body User user);

    /**
     * 로그인 retrofit url
     * @param user
     * @return
     */
    @POST("/app/users/login")
    Call<LoginResponse> login(@Body User user);

    /**
     * Id 중복 retrofit url
     * @param user
     * @return
     */
    @POST("/app/users/duplication")
    Call<DuplicateResponse> duplicate(@Body User user);
}
