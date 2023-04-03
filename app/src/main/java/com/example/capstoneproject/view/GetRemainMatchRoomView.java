package com.example.capstoneproject.view;

import com.example.capstoneproject.data.getmatchdetail.response.GetMatchRoomDetailResult;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResult;

import java.util.List;

public interface GetRemainMatchRoomView {

    void onGetRemainMatchRoomSuccess(GetRemainMatchRoomResponse result);
    void onGetRemainMatchRoomFailure();
}
