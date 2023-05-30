package com.example.capstoneproject.view;

import com.example.capstoneproject.data.game.response.CheckSocketActiveResult;

public interface CheckSocketActiveView {

    /**
     * 소켓 활성 여부 확인 API 성공 시 호출
     *
     * @param result 소켓 활성 여부 확인  response의 result
     */
    void onCheckSocketActiveViewSuccess(CheckSocketActiveResult result);

    /**
     * 소켓 활성 여부 확인 API 실패 시 호출
     */
    void onCheckSocketActiveViewFailure();
}
