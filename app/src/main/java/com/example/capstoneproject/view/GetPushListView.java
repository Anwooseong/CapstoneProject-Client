package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.push.GetPushListResult;

import java.util.List;

public interface GetPushListView {
    void onGetPushListSuccess(List<GetPushListResult> result);

    void onGetPushListFailure();
}
