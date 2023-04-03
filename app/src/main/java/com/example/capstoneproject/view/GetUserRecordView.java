package com.example.capstoneproject.view;

import com.example.capstoneproject.data.users.response.record.GetRecordResult;

import java.util.List;

public interface GetUserRecordView {
    void onGetMatchRoomSuccess(List<GetRecordResult> result);
    void onGetMatchRoomFailure();
}
