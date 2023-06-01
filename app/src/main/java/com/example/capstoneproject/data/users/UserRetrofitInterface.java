package com.example.capstoneproject.data.users;

import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResponse;
import com.example.capstoneproject.data.users.response.info.GetUserInfoResponse;
import com.example.capstoneproject.data.users.response.push.GetPushListResponse;
import com.example.capstoneproject.data.users.response.record.GetRecordResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserRetrofitInterface {
    /**
     * 전적 기록 목록 조회 retrofit url
     * @param jwt
     * @return
     */
    @GET("/app/users/records")
    Call<GetRecordResponse> getRecordRes(@Header("X-ACCESS-TOKEN") String jwt);

    /**
     * 알림 목록 조회 retrofit url
     * @param jwt
     * @return
     */
    @GET("/app/users/pushes")
    Call<GetPushListResponse> getPushListRes(@Header("X-ACCESS-TOKEN") String jwt);

    /**
     * 간단 내 정보 retrofit url
     * @param jwt
     * @return
     */
    @GET("/app/users/simple-info")
    Call<GetSimpleInfoResponse> getSimpleInfoRes(@Header("X-ACCESS-TOKEN") String jwt);

    /**
     * 내 정보 retrofit url
     * @param jwt
     * @return
     */
    @GET("/app/users/info")
    Call<GetUserInfoResponse> getUserInfoRes(@Header("X-ACCESS-TOKEN") String jwt);
}
