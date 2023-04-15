package com.example.capstoneproject.view;


import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.data.push.response.ApplyPushMatchResult;

public interface ApplyPushMatchView {

    void onApplyPushMatchSuccess(String jwt, ApplyPushMatchResult applyPushMatchResult);
    void onApplyPushMatchFailure();
}
