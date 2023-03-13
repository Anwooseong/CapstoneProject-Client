package com.example.capstoneproject.data.getmatch;

import android.util.Log;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResponse;
import com.example.capstoneproject.view.GetMatchRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMatchRoomService {
    private final GetMatchRetrofitInterface getMatchRetrofitInterface = NetworkModule.getRetrofit().create(GetMatchRetrofitInterface.class);
    private GetMatchRoomView getMatchRoomView;

    public void setGetMatchRoomView(GetMatchRoomView getMatchRoomView) {
        this.getMatchRoomView = getMatchRoomView;
    }

    //GET
    public void getOnlineMatchRoom(){
        getMatchRetrofitInterface.getMatchRoom("ONLINE").enqueue(new Callback<GetMatchRoomResponse>() {
            @Override
            public void onResponse(Call<GetMatchRoomResponse> call, Response<GetMatchRoomResponse> response) {
                GetMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000){
                    getMatchRoomView.onGetMatchRoomSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<GetMatchRoomResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 매칭방 조회 실패");
            }
        });
    }

    //GET
    public void getOfflineMatchRoom(){
        getMatchRetrofitInterface.getMatchRoom("OFFLINE").enqueue(new Callback<GetMatchRoomResponse>() {
            @Override
            public void onResponse(Call<GetMatchRoomResponse> call, Response<GetMatchRoomResponse> response) {
                GetMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000){
                    getMatchRoomView.onGetMatchRoomSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<GetMatchRoomResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 매칭방 조회 실패");
            }
        });
    }

}
