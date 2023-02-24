package com.example.capstoneproject.data.auth;

import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.LoginResponse;
import com.example.capstoneproject.data.auth.response.DuplicateResponse;
import com.example.capstoneproject.data.auth.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthRetrofitInterface {
    @POST("/app/users")
    Call<SignUpResponse> signUp(@Body User user);

    @POST("/app/users/login")
    Call<LoginResponse> login(@Body User user);

    @POST("/app/users/duplication")
    Call<DuplicateResponse> duplicate(@Body User user);
}
