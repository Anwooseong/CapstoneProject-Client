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
    /**
     * 매칭 신청 retrofit url
     * @param jwt
     * @param applyPushMatchReq
     * @return
     */
    @POST("/app/pushes")
    Call<ApplyPushMatchRes> applyPushMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body ApplyPushMatchReq applyPushMatchReq);

    /**
     * 매칭 수락 retrofit url
     * @param jwt
     * @param postAcceptMatchReq
     * @return
     */
    @POST("/app/pushes/permission")
    Call<PostAcceptMatchRes> acceptMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body PostAcceptMatchReq postAcceptMatchReq);

    /**
     * 매칭 거절 retrofit url
     * @param jwt
     * @param postCancelMatchReq
     * @return
     */
    @POST("/app/pushes/cancel")
    Call<PostCancelMatchRes> cancelMatch(@Header("X-ACCESS-TOKEN") String jwt, @Body PostCancelMatchReq postCancelMatchReq);
}
