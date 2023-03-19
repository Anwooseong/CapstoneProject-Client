package com.example.capstoneproject.data.match;

import static com.example.capstoneproject.data.NetworkModule.getRetrofit;

import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;
import com.example.capstoneproject.view.GetRemainMatchRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchService {
    private final MatchRetrofitInterface matchService = getRetrofit().create(MatchRetrofitInterface.class);
    private CreateMatchRoomView createMatchRoomView;
    private GetRemainMatchRoomView getRemainMatchRoomView;
    public void setCreateMatchRoomView(CreateMatchRoomView createMatchRoomView){
        this.createMatchRoomView = createMatchRoomView;
    }

    public void setGetRemainMatchRoomView(GetRemainMatchRoomView getRemainMatchRoomView) {
        this.getRemainMatchRoomView = getRemainMatchRoomView;
    }

    // POST
    public void postMatchRoom(String jwt, PostMatchRoom postMatchRoom){
        matchService.createMatchRoom(jwt, postMatchRoom).enqueue(new Callback<PostMatchRoomResponse>() {
            @Override
            public void onResponse(Call<PostMatchRoomResponse> call, Response<PostMatchRoomResponse> response) {
                PostMatchRoomResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    createMatchRoomView.onCreateMatchRoomSuccess();
                }else {
                    createMatchRoomView.onCreateMatchRoomFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<PostMatchRoomResponse> call, Throwable t) {}
        });
    }

    //GET
    public void getRemainResult(String jwt) {
        matchService.getRemainingMatchRoom(jwt).enqueue(new Callback<GetRemainMatchRoomResponse>() {
            @Override
            public void onResponse(Call<GetRemainMatchRoomResponse> call, Response<GetRemainMatchRoomResponse> response) {
                GetRemainMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getRemainMatchRoomView.onGetRemainMatchRoomSuccess(resp);
                }
            }

            @Override
            public void onFailure(Call<GetRemainMatchRoomResponse> call, Throwable t) {

            }
        });
    }
}
