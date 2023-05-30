package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;

public interface GetDetailMatchView {

    /**
     * 상세 매치 조회 API 성공했을 때 호출
     *
     * @param resp 상세 매치 response
     */
    void onDetailMatchSuccess(GetDetailMatchResponse resp);

    /**
     * 상세 매치 조회 API 실패했을 때 호출
     */
    void onDetailMatchFailure();
}
