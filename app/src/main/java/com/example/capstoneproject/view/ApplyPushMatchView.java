package com.example.capstoneproject.view;


import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;

public interface ApplyPushMatchView {

    void onApplyPushMatchSuccess(String jwt, ApplyPushMatchReq applyPushMatchReq);
    void onApplyPushMatchFailure();
}
