package com.example.capstoneproject.data.push;

import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.data.push.request.PostAcceptMatchReq;
import com.example.capstoneproject.data.push.request.PostCancelMatchReq;
import com.example.capstoneproject.data.push.response.ApplyPushMatchRes;
import com.example.capstoneproject.data.push.response.PostAcceptMatchRes;
import com.example.capstoneproject.data.push.response.PostCancelMatchRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PushRetrofitInterface {
    @POST("/app/pushes")
    Call<ApplyPushMatchRes> applyPushMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body ApplyPushMatchReq applyPushMatchReq);

    @POST("/app/pushes/permission")
    Call<PostAcceptMatchRes> acceptMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body PostAcceptMatchReq postAcceptMatchReq);

    @POST("/app/pushes/cancel")
    Call<PostCancelMatchRes> cancelMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body PostCancelMatchReq postCancelMatchReq);
}
