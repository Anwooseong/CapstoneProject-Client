package com.example.capstoneproject.view;

import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResult;
import com.example.capstoneproject.data.getmatchdetail.response.GetMatchRoomDetailResult;

import java.util.List;

public interface GetMatchRoomDetailView {
    void onGetMatchRoomSuccess(GetMatchRoomDetailResult result);
    void onGetMatchRoomFailure();
}
