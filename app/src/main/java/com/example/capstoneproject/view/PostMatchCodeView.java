package com.example.capstoneproject.view;

import com.example.capstoneproject.data.game.response.PostMatchCodeResult;

public interface PostMatchCodeView {

    /**
     * 매치 코드 전송 API 성공했을 때 호출
     * Admin Activity에서 선수들의  historyIdx를 초기화하고 textView를 닉네임으로 setText 한다.
     * @param result 전송된 매치 코드를 나타내는 PostMatchCodeResult 객체
     */
    void onPostMatchCodeSuccess(PostMatchCodeResult result);

    /**
     * 매치 코드 전송 API 실패했을 때 호출
     */
    void onPostMatchCodeFailure();
}
