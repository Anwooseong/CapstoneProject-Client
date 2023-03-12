package com.example.capstoneproject.data.match;

import static com.example.capstoneproject.data.NetworkModule.getRetrofit;

import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchService {
    private final MatchRetrofitInterface matchService = getRetrofit().create(MatchRetrofitInterface.class);
    private CreateMatchRoomView createMatchRoomView;
    public void setCreateMatchRoomView(CreateMatchRoomView createMatchRoomView){
        this.createMatchRoomView = createMatchRoomView;
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
}
