package com.uou.capstoneproject.view;

import com.uou.capstoneproject.data.auth.response.duplicateuid.DuplicateResponse;

public interface DuplicateView {
    /**
     * 중복 확인 API 성공했을 때 호출
     */
    void onCheckedSuccess();

    /**
     * 중복 확인이 API 실패했을 때 호출
     *
     * @param resp 중복 확인 response
     */
    void onCheckedFailure(DuplicateResponse resp);
}
