package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchCityResponse;

public interface GetMatchCityView {
    void onGetMatchCitySuccess(GetMatchCityResponse response);
    void onGetMatchCityFailure();
}
