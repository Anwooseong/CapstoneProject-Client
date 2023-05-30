package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchRoomResult;

import java.util.List;

public interface GetMatchRoomView {

    /**
     * 매치 방 목록 조회 API 성공했을 때 호출
     *
     * @param result 매치 방 목록
     */
    void onGetMatchRoomSuccess(List<GetMatchRoomResult> result);

    /**
     * 매치 방 목록 조회 API 실패했을 때 호출
     */
    void onGetMatchRoomFailure();
}
