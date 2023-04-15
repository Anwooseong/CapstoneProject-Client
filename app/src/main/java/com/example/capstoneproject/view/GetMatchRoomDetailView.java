package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchRoomDetailResult;

public interface GetMatchRoomDetailView {
    void onGetMatchRoomSuccess(GetMatchRoomDetailResult result);
    void onGetMatchRoomFailure();
}
