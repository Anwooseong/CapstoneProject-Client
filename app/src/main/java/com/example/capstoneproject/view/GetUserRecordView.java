package com.example.capstoneproject.view;

import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResult;
import com.example.capstoneproject.data.users.response.GetRecordResult;

import java.util.List;

public interface GetUserRecordView {
    void onGetMatchRoomSuccess(List<GetRecordResult> result);
    void onGetMatchRoomFailure();
}
