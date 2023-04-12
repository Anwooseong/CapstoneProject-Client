package com.example.capstoneproject.data.match;

import static com.example.capstoneproject.data.NetworkModule.getRetrofit;

import android.util.Log;

import com.example.capstoneproject.data.match.response.GetMatchRoomDetailResponse;
import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.GetMatchRoomResponse;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;
import com.example.capstoneproject.view.GetDetailMatchView;
import com.example.capstoneproject.view.GetMatchRoomDetailView;
import com.example.capstoneproject.view.GetMatchRoomView;
import com.example.capstoneproject.view.GetRemainMatchRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchService {
    private final MatchRetrofitInterface matchService = getRetrofit().create(MatchRetrofitInterface.class);
    private CreateMatchRoomView createMatchRoomView;
    private GetRemainMatchRoomView getRemainMatchRoomView;
    private GetDetailMatchView getDetailMatchView;
    private GetMatchRoomDetailView getMatchRoomDetailView;

    public void setGetMatchRoomDetailView(GetMatchRoomDetailView getMatchRoomDetailView) {
        this.getMatchRoomDetailView = getMatchRoomDetailView;
    }

    public void setCreateMatchRoomView(CreateMatchRoomView createMatchRoomView) {
        this.createMatchRoomView = createMatchRoomView;
    }

    public void setGetRemainMatchRoomView(GetRemainMatchRoomView getRemainMatchRoomView) {
        this.getRemainMatchRoomView = getRemainMatchRoomView;
    }

    public void setGetDetailMatchView(GetDetailMatchView getDetailMatchView) {
        this.getDetailMatchView = getDetailMatchView;
    }

    // POST
    public void postMatchRoom(String jwt, PostMatchRoom postMatchRoom) {
        matchService.createMatchRoom(jwt, postMatchRoom).enqueue(new Callback<PostMatchRoomResponse>() {
            @Override
            public void onResponse(Call<PostMatchRoomResponse> call, Response<PostMatchRoomResponse> response) {
                PostMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    createMatchRoomView.onCreateMatchRoomSuccess();
                } else {
                    createMatchRoomView.onCreateMatchRoomFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<PostMatchRoomResponse> call, Throwable t) {
            }
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

    //GET
    public void getDetailMatchResult(String jwt, int matchIdx) {
        matchService.getDetailMatch(jwt, matchIdx).enqueue(new Callback<GetDetailMatchResponse>() {
            @Override
            public void onResponse(Call<GetDetailMatchResponse> call, Response<GetDetailMatchResponse> response) {
                GetDetailMatchResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getDetailMatchView.onDetailMatchSuccess(resp);
                }
            }

            @Override
            public void onFailure(Call<GetDetailMatchResponse> call, Throwable t) {
                getDetailMatchView.onDetailMatchFailure();
            }
        });
    }

    private GetMatchRoomView getMatchRoomView;

    public void setGetMatchRoomView(GetMatchRoomView getMatchRoomView) {
        this.getMatchRoomView = getMatchRoomView;
    }

    //GET
    public void getOnlineMatchRoom() {
        matchService.getMatchRoom("ONLINE").enqueue(new Callback<GetMatchRoomResponse>() {
            @Override
            public void onResponse(Call<GetMatchRoomResponse> call, Response<GetMatchRoomResponse> response) {
                GetMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
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
    public void getOfflineMatchRoom() {
        matchService.getMatchRoom("OFFLINE").enqueue(new Callback<GetMatchRoomResponse>() {
            @Override
            public void onResponse(Call<GetMatchRoomResponse> call, Response<GetMatchRoomResponse> response) {
                GetMatchRoomResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getMatchRoomView.onGetMatchRoomSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<GetMatchRoomResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: 매칭방 조회 실패");
            }
        });
    }


    public void getMatchRoomDetail(int matchIdx) {
        matchService.getMatchRoomDetail(matchIdx).enqueue(new Callback<GetMatchRoomDetailResponse>() {
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
