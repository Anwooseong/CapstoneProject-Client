package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchRoomDetailResult;

public interface GetMatchRoomDetailView {

    /**
     * 매치 룸 상세 정보 조회 API 성공했을 때 호출
     *
     * @param result 매칭방 상세 정보 response의 result
     */
    void onGetMatchRoomSuccess(GetMatchRoomDetailResult result);

    /**
     * 매칭방 상세 정보 조회 API 실패했을 때 호출
     */
    void onGetMatchRoomFailure();
}
