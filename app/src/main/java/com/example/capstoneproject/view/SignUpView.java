package com.example.capstoneproject.view;

import com.example.capstoneproject.data.auth.response.AuthResponse;

public interface SignUpView {
    void onSignUpSuccess();
    void onSignUpFailure(AuthResponse response);
}
