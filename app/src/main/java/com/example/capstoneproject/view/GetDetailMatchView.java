package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;

public interface GetDetailMatchView {

    void onDetailMatchSuccess(GetDetailMatchResponse resp);
    void onDetailMatchFailure();
}
