package com.uou.capstoneproject.view;

import com.uou.capstoneproject.data.game.response.PostGameEndResult;

public interface PostGameEndView {


    /**
     * 게임 종료 API 성공했을 때 호출
     *
     * @param postGameEndResult 게임 종료 결과 데이터
     */
    void onPostGameSuccess(PostGameEndResult postGameEndResult);

    /**
     * 게임 종료 API 실패했을 때 호출
     */
    void onPostGameFailure();
}
