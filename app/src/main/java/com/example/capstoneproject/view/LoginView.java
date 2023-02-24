package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.LoginResponse;
import com.example.capstoneproject.data.auth.response.result.LoginResult;

public interface LoginView {
    void onLoginSuccess(int code, LoginResult result);
    void onLoginFailure();
}
