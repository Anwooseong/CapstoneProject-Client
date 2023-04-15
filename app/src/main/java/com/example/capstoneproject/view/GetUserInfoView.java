package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.info.GetUserInfoResult;

public interface GetUserInfoView {
    void onGetUserInfoSuccess(GetUserInfoResult result);
    void onGetUserInfoFailure();
}
