package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResult;

public interface GetSimpleInfoView {

    /**
     * 간단 내 정보 조회 API 성공했을 때 호출
     *
     * @param result 간단한 정보 응답 데이터
     */
    void onGetSimpleInfoSuccess(GetSimpleInfoResult result);

    /**
     * 간단 내 정보 조회 API 실패했을 때 호출
     */
    void onGetSimpleInfoFailure();
}
