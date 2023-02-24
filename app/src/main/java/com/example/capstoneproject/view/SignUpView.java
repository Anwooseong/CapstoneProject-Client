package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.LoginResponse;
import com.example.capstoneproject.data.auth.response.SignUpResponse;

public interface SignUpView {
    void onSignUpSuccess();
    void onSignUpFailure(SignUpResponse response);
}
