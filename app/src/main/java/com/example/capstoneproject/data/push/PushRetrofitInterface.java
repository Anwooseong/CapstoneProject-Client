package com.example.capstoneproject.data.push;

import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.data.push.response.ApplyPushMatchRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PushRetrofitInterface {
    @POST("/app/pushes")
    Call<ApplyPushMatchRes> applyPushMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body ApplyPushMatchReq applyPushMatchReq);
}
