package com.uou.capstoneproject.view;


import com.uou.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;

public interface GetAllOnlineMatchCountView {

    /**
     * 모든 온라인 매치 카운트 조회 API 성공했을 때 호출
     *
     * @param response 모든 온라인 매치 카운트 response
     */
    void onGetAllOnlineMatchCountSuccess(GetAllOnlineMatchCountResponse response);

    /**
     * 모든 온라인 매치 카운트 조회 API 실패했을 때 호출
     *
     * @param response 모든 온라인 매치 카운트 response
     */
    void onGetAllOnlineMatchCountFailure(GetAllOnlineMatchCountResponse response);
}
