package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;

public interface CreateMatchRoomView {
    void onCreateMatchRoomSuccess();
    void onCreateMatchRoomFailure(PostMatchRoomResponse postMatchRoomResponse);
}
