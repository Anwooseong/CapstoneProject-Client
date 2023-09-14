package com.uou.capstoneproject.view;


import com.uou.capstoneproject.data.match.response.GetAllOfflineMatchCountResponse;

public interface GetAllOfflineMatchCountView {

    /**
     * 모든 오프라인 매치 카운트 조회 API 성공했을 때 호출
     *
     * @param response 모든 오프라인 매치 카운트 response
     */
    void onGetAllOfflineMatchCountSuccess(GetAllOfflineMatchCountResponse response);

    /**
     * 모든 오프라인 매치 카운트 조회가 실패했을 때 호출
     *
     * @param response 모든 오프라인 매치 카운트 response
     */
    void onGetAllOfflineMatchCountFailure(GetAllOfflineMatchCountResponse response);
}
