package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;

public interface GetRemainMatchRoomView {

    void onGetRemainMatchRoomSuccess(GetRemainMatchRoomResponse result);
    void onGetRemainMatchRoomFailure();
}
