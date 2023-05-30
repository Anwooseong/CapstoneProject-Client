package com.example.capstoneproject.view;

import com.example.capstoneproject.data.match.response.GetMatchCityResponse;

public interface GetMatchCityView {

    /**
     * 행정구 목록 조회 API 성공했을 때 호출
     *
     * @param response 스피너에 넣어줄 도시명(행정구) response
     */
    void onGetMatchCitySuccess(GetMatchCityResponse response);

    /**
     * 행정구 목록 조회 API 실패했을 때 호출
     */
    void onGetMatchCityFailure();
}
