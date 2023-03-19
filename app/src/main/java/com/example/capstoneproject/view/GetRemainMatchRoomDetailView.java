package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResult;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResultDetail;

import java.util.List;

public interface GetRemainMatchRoomDetailView {

    void onGetRemainMatchRoomDetailSuccess(GetRemainMatchRoomResultDetail result);
    void onGetRemainMatchRoomDetailFailure();
}
