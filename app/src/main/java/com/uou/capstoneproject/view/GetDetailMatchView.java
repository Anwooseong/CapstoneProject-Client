package com.uou.capstoneproject.view;

import com.uou.capstoneproject.data.match.response.plan.GetDetailMatchResponse;

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
