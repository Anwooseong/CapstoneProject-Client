package com.example.capstoneproject.view;


import com.example.capstoneproject.data.match.response.GetAllMatchCountResponse;

public interface GetAllMatchCountView {
    /**
     * 모든 매치 카운트 조회 API 성공했을 때 호출
     *
     * @param response 모든 매치 카운트 response
     */
    void onGetAllMatchCountSuccess(GetAllMatchCountResponse response);

    /**
     * 모든 매치 카운트 조회 API  실패했을 때 호출
     *
     * @param response 모든 매치 카운트 response
     */
    void onGetAllMatchCountFailure(GetAllMatchCountResponse response);
}
