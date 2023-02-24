package com.example.capstoneproject.data.auth;

import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.AuthResponse;
import com.example.capstoneproject.data.auth.response.DuplicateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthRetrofitInterface {
    @POST("/app/users")
    Call<AuthResponse> signUp(@Body User user);

    @POST("/app/users/login")
    Call<AuthResponse> login(@Body User user);

    @POST("/app/users/duplication")
    Call<DuplicateResponse> duplicate(@Body User user);
}
