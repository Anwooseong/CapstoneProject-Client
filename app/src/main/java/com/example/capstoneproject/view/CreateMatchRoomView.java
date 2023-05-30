package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;

public interface CreateMatchRoomView {

    /**
     * 매치 룸 생성 API 성공 시 호출
     */
    void onCreateMatchRoomSuccess();

    /**
     * 매치 룸 생성 API 실패 시 호출
     *
     * @param postMatchRoomResponse 매치 룸 생성 response
     */
    void onCreateMatchRoomFailure(PostMatchRoomResponse postMatchRoomResponse);
}
