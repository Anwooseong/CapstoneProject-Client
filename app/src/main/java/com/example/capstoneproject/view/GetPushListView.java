package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.push.GetPushListResult;

import java.util.List;

public interface GetPushListView {

    /**
     * 푸시 알림 목록 조회 API 성공했을 때 호출
     *
     * @param result 푸시 알림 목록
     */
    void onGetPushListSuccess(List<GetPushListResult> result);

    /**
     * 푸시 알림 목록 조회 API 실패했을 때 호출
     */
    void onGetPushListFailure();
}
