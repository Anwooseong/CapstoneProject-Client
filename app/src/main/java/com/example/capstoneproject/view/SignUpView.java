package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.signup.SignUpResponse;

public interface SignUpView {
    void onSignUpSuccess();
    void onSignUpFailure(SignUpResponse response);
}
