package com.uou.capstoneproject.view;

import com.uou.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;

public interface GetRemainMatchRoomView {

    /**
     * 예정 매치 목록 조회 API 성공했을 때 호출
     *
     * @param result 예정 매치 목록
     */
    void onGetRemainMatchRoomSuccess(GetRemainMatchRoomResponse result);

    /**
     * 예정 매치 목록 조회 API 실패했을 때 호출
     */
    void onGetRemainMatchRoomFailure();
}
