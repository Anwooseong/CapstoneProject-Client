package com.example.capstoneproject.view;


import com.example.capstoneproject.data.push.request.PostAcceptMatchReq;

public interface PostAcceptMatchView {
    void onAcceptMatchSuccess(String jwt, PostAcceptMatchReq postAcceptMatchReq);
    void onAcceptMatchFailure();
}
