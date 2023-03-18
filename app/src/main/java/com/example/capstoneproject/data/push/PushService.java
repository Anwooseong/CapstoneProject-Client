package com.example.capstoneproject.data.push;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.push.request.ApplyPushMatchReq;
import com.example.capstoneproject.data.push.request.PostAcceptMatchReq;
import com.example.capstoneproject.data.push.response.ApplyPushMatchRes;
import com.example.capstoneproject.data.push.response.PostAcceptMatchRes;
import com.example.capstoneproject.view.ApplyPushMatchView;
import com.example.capstoneproject.view.PostAcceptMatchView;

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

    public void applyPushMatch(String jwt, ApplyPushMatchReq applyPushMatchReq){
        pushRetrofitInterface.applyPushMatch(jwt, applyPushMatchReq).enqueue(new Callback<ApplyPushMatchRes>() {
            @Override
            public void onResponse(Call<ApplyPushMatchRes> call, Response<ApplyPushMatchRes> response) {
                ApplyPushMatchRes resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    applyPushMatchView.onApplyPushMatchSuccess(jwt, applyPushMatchReq);
                } else {
                    applyPushMatchView.onApplyPushMatchFailure();
                }
            }

            @Override
            public void onFailure(Call<ApplyPushMatchRes> call, Throwable t) {

            }
        });
    }

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

}
