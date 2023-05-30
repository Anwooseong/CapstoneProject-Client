package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.info.GetUserInfoResult;

public interface GetUserInfoView {

    /**
     * 내 정보 조회 API 성공했을 때 호출
     *
     * @param result 사용자 정보 응답 데이터
     */
    void onGetUserInfoSuccess(GetUserInfoResult result);

    /**
     * 내 정보 조회 API 실패했을 때 호출
     */
    void onGetUserInfoFailure();
}
