package com.uou.capstoneproject.data.push;

import com.uou.capstoneproject.data.NetworkModule;
import com.uou.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.uou.capstoneproject.data.push.request.PostAcceptMatchReq;
import com.uou.capstoneproject.data.push.request.PostCancelMatchReq;
import com.uou.capstoneproject.data.push.response.ApplyPushMatchRes;
import com.uou.capstoneproject.data.push.response.PostAcceptMatchRes;
import com.uou.capstoneproject.data.push.response.PostCancelMatchRes;
import com.uou.capstoneproject.view.ApplyPushMatchView;
import com.uou.capstoneproject.view.PostAcceptMatchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushService {
    private final PushRetrofitInterface pushRetrofitInterface = NetworkModule.getRetrofit().create(PushRetrofitInterface.class);
    private ApplyPushMatchView applyPushMatchView;
    private PostAcceptMatchView postAcceptMatchView;


    public void setApplyPushMatchView(ApplyPushMatchView applyPushMatchView) {
        this.applyPushMatchView = applyPushMatchView;
    }

    public void setPostAcceptMatchView(PostAcceptMatchView postAcceptMatchView) {
        this.postAcceptMatchView = postAcceptMatchView;
    }

    /**
     * 매칭 신청 API
     * @param jwt
     * @param applyPushMatchReq
     */
    public void applyPushMatch(String jwt, ApplyPushMatchReq applyPushMatchReq){
        pushRetrofitInterface.applyPushMatch(jwt, applyPushMatchReq).enqueue(new Callback<ApplyPushMatchRes>() {
            @Override
            public void onResponse(Call<ApplyPushMatchRes> call, Response<ApplyPushMatchRes> response) {
                ApplyPushMatchRes resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    applyPushMatchView.onApplyPushMatchSuccess(jwt, resp.getResult());
                } else {
                    applyPushMatchView.onApplyPushMatchFailure();
                }
            }

            @Override
            public void onFailure(Call<ApplyPushMatchRes> call, Throwable t) {

            }
        });
    }

    /**
     * 매칭 수락 API
     * @param jwt
     * @param postAcceptMatchReq
     */
    public void postAcceptMatch(String jwt, PostAcceptMatchReq postAcceptMatchReq){
        pushRetrofitInterface.acceptMatch(jwt, postAcceptMatchReq).enqueue(new Callback<PostAcceptMatchRes>() {
            @Override
            public void onResponse(Call<PostAcceptMatchRes> call, Response<PostAcceptMatchRes> response) {
                PostAcceptMatchRes resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    postAcceptMatchView.onAcceptMatchSuccess(jwt, postAcceptMatchReq);
                } else {
                    postAcceptMatchView.onAcceptMatchFailure();
                }
            }

            @Override
            public void onFailure(Call<PostAcceptMatchRes> call, Throwable t) {

            }
        });
    }

    /**
     * 매칭 거절 API
     * @param jwt
     * @param postCancelMatchReq
     */
    public void postCancelMatch(String jwt, PostCancelMatchReq postCancelMatchReq) {
        pushRetrofitInterface.cancelMatch(jwt, postCancelMatchReq).enqueue(new Callback<PostCancelMatchRes>() {
            @Override
            public void onResponse(Call<PostCancelMatchRes> call, Response<PostCancelMatchRes> response) {
                PostCancelMatchRes resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                }
            }

            @Override
            public void onFailure(Call<PostCancelMatchRes> call, Throwable t) {

            }
        });
    }

}
