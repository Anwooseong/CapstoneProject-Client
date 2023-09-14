package com.uou.capstoneproject.view;

import com.uou.capstoneproject.data.users.response.record.GetRecordResult;

import java.util.List;

public interface GetUserRecordView {

    /**
     * 전적 목록 조회 API 성공했을 때 호출
     *
     * @param result 전적 목록 응답 데이터
     */
    void onGetMatchRoomSuccess(List<GetRecordResult> result);

    /**
     * 전적 목록 조회 API 실패했을 때 호출
     */
    void onGetMatchRoomFailure();
}
