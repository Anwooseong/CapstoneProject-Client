package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.login.LoginResult;

public interface LoginView {
    void onLoginSuccess(int code, LoginResult result);
    void onLoginFailure();
}
