package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchRoomResult;

import java.util.List;

public interface GetMatchRoomView {
    void onGetMatchRoomSuccess(List<GetMatchRoomResult> result);
    void onGetMatchRoomFailure();
}
