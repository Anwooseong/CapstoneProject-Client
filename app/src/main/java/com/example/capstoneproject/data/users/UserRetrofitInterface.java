package com.example.capstoneproject.data.users;

import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResponse;
import com.example.capstoneproject.data.users.response.info.GetUserInfoResponse;
import com.example.capstoneproject.data.users.response.push.GetPushListResponse;
import com.example.capstoneproject.data.users.response.record.GetRecordResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserRetrofitInterface {
    @GET("/app/users/records")
    Call<GetRecordResponse> getRecordRes(@Header("X-ACCESS-TOKEN") String jwt);

    @GET("/app/users/pushes")
    Call<GetPushListResponse> getPushListRes(@Header("X-ACCESS-TOKEN") String jwt);

    @GET("/app/users/simple-info")
    Call<GetSimpleInfoResponse> getSimpleInfoRes(@Header("X-ACCESS-TOKEN") String jwt);

    @GET("/app/users/info")
    Call<GetUserInfoResponse> getUserInfoRes(@Header("X-ACCESS-TOKEN") String jwt);
}
