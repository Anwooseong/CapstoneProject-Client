package com.example.capstoneproject.data.getmatchdetail;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.getmatchdetail.response.GetMatchRoomDetailResponse;
import com.example.capstoneproject.view.GetMatchRoomDetailView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMatchRoomDetailService {
    private final GetMatchRoomDetailRetrofitInterface getMatchRoomDetailRetrofitInterface = NetworkModule.getRetrofit().create(GetMatchRoomDetailRetrofitInterface.class);
    private GetMatchRoomDetailView getMatchRoomDetailView;

    public GetMatchRoomDetailService(GetMatchRoomDetailView getMatchRoomDetailView) {
        this.getMatchRoomDetailView = getMatchRoomDetailView;
    }

    public void getMatchRoomDetail(int matchIdx) {
        getMatchRoomDetailRetrofitInterface.getMatchRoomDetail(matchIdx).enqueue(new Callback<GetMatchRoomDetailResponse>() {
            @Override
            public void onResponse(Call<GetMatchRoomDetailResponse> call, Response<GetMatchRoomDetailResponse> response) {
                GetMatchRoomDetailResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getMatchRoomDetailView.onGetMatchRoomSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<GetMatchRoomDetailResponse> call, Throwable t) {

            }
        });
    }
}

