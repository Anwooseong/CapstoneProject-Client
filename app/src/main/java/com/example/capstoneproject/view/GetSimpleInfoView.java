package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResult;

public interface GetSimpleInfoView {
    void onGetSimpleInfoSuccess(GetSimpleInfoResult result);
    void onGetSimpleInfoFailure();
}
