package com.example.capstoneproject.view;


import com.example.capstoneproject.data.push.request.PostAcceptMatchReq;

public interface PostAcceptMatchView {

    /**
     * 매치 승인/거절 API 성공했을 때 호출
     *
     * @param jwt                 JWT 토큰
     * @param postAcceptMatchReq  승인/거절한 매치 요청 데이터(new PostAcceptMatchReq(getResult.getPushIdx(), getResult.getOwnerUserIdx(), getResult.getJoinUserIdx(), getResult.getMatchIdx(), accept);)
     */
    void onAcceptMatchSuccess(String jwt, PostAcceptMatchReq postAcceptMatchReq);

    /**
     * 매치 승인/거절 API 실패했을 때 호출
     */
    void onAcceptMatchFailure();
}
