package com.example.capstoneproject.view;


import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.data.push.response.ApplyPushMatchResult;

public interface ApplyPushMatchView {

    /**
     * 푸시 매치 신청 API 성공 시 호출
     *
     * @param jwt                  JWT 토큰
     * @param applyPushMatchResult 푸시 매치 신청 response의 result
     */
    void onApplyPushMatchSuccess(String jwt, ApplyPushMatchResult applyPushMatchResult);

    /**
     * 푸시 매치 신청 API 실패 시 호출
     */
    void onApplyPushMatchFailure();
}
