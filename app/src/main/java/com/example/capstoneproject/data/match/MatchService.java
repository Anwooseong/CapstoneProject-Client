package com.example.capstoneproject.data.match;

import static com.example.capstoneproject.data.NetworkModule.getRetrofit;

import android.util.Log;

import com.example.capstoneproject.data.match.response.GetAllMatchCountResponse;
import com.example.capstoneproject.data.match.response.GetAllOfflineMatchCountResponse;
import com.example.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;
import com.example.capstoneproject.data.match.response.GetMatchCityResponse;
import com.example.capstoneproject.data.match.response.GetMatchRoomDetailResponse;
import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.GetMatchRoomResponse;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;
import com.example.capstoneproject.view.GetAllMatchCountView;
import com.example.capstoneproject.view.GetAllOfflineMatchCountView;
import com.example.capstoneproject.view.GetAllOnlineMatchCountView;
import com.example.capstoneproject.view.GetDetailMatchView;
import com.example.capstoneproject.view.GetMatchCityView;
import com.example.capstoneproject.view.GetMatchRoomDetailView;
import com.example.capstoneproject.view.GetMatchRoomView;
import com.example.capstoneproject.view.GetRemainMatchRoomView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchService {
    private final MatchRetrofitInterface matchService = getRetrofit().create(MatchRetrofitInterface.class);
    private GetAllMatchCountView getAllMatchCountView;
    private GetAllOnlineMatchCountView getAllOnlineMatchCountView;
    private GetAllOfflineMatchCountView getAllOfflineMatchCountView;
    private CreateMatchRoomView createMatchRoomView;
    private GetRemainMatchRoomView getRemainMatchRoomView;
    private GetDetailMatchView getDetailMatchView;
    private GetMatchRoomDetailView getMatchRoomDetailView;
    private GetMatchCityView getMatchCityView;
    private GetMatchRoomView getMatchRoomView;

    public void setGetAllOfflineMatchCountView(GetAllOfflineMatchCountView getAllOfflineMatchCountView) {
        this.getAllOfflineMatchCountView = getAllOfflineMatchCountView;
    }

    public void setGetAllOnlineMatchCountView(GetAllOnlineMatchCountView getAllOnlineMatchCountView) {
        this.getAllOnlineMatchCountView = getAllOnlineMatchCountView;
    }

    public void setGetMatchRoomView(GetMatchRoomView getMatchRoomView) {
        this.getMatchRoomView = getMatchRoomView;
    }

    public void setGetAllMatchCountView(GetAllMatchCountView getAllMatchCountView) {
        this.getAllMatchCountView = getAllMatchCountView;
    }

    public void setGetMatchCityView(GetMatchCityView getMatchCityView) {
        this.getMatchCityView = getMatchCityView;
    }

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

    //GET
    public void getOnlineMatchRoom() {
        matchService.getMatchRoom("ONLINE", null, null).enqueue(new Callback<GetMatchRoomResponse>() {
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
            }
        });
    }

    //GET
    public void getOfflineMatchRoom(String local, String city) {
        matchService.getMatchRoom("OFFLINE", local, city).enqueue(new Callback<GetMatchRoomResponse>() {
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

    public void getMatchCity(String local) {
        matchService.getMatchCity(local).enqueue(new Callback<GetMatchCityResponse>() {
            @Override
            public void onResponse(Call<GetMatchCityResponse> call, Response<GetMatchCityResponse> response) {
                GetMatchCityResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getMatchCityView.onGetMatchCitySuccess(resp);
                }
            }

            @Override
            public void onFailure(Call<GetMatchCityResponse> call, Throwable t) {

            }
        });
    }

    public void getAllMatchCount() {
        matchService.getAllMatchCount().enqueue(new Callback<GetAllMatchCountResponse>() {
            @Override
            public void onResponse(Call<GetAllMatchCountResponse> call, Response<GetAllMatchCountResponse> response) {
                GetAllMatchCountResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getAllMatchCountView.onGetAllMatchCountSuccess(resp);
                } else {
                    getAllMatchCountView.onGetAllMatchCountFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<GetAllMatchCountResponse> call, Throwable t) {

            }
        });
    }

    public void getAllOnlineMatchCount() {
        matchService.getAllOnlineMatchCount().enqueue(new Callback<GetAllOnlineMatchCountResponse>() {
            @Override
            public void onResponse(Call<GetAllOnlineMatchCountResponse> call, Response<GetAllOnlineMatchCountResponse> response) {
                GetAllOnlineMatchCountResponse resp = response.body();
                assert resp != null;
                if (resp.getCode() == 1000) {
                    getAllOnlineMatchCountView.onGetAllOnlineMatchCountSuccess(resp);
                } else {
                    getAllOnlineMatchCountView.onGetAllOnlineMatchCountFailure(resp);
                }
            }

            @Override
            public void onFailure(Call<GetAllOnlineMatchCountResponse> call, Throwable t) {

            }
        });
    }

    public void getAllOfflineMatchCount(String localName, String cityName) {
        matchService.getAllOfflineMatchCount(localName, cityName).enqueue(new Callback<GetAllOfflineMatchCountResponse>() {
            @Override
            public void onResponse(Call<GetAllOfflineMatchCountResponse> call, Response<GetAllOfflineMatchCountResponse> response) {
                matchService.getAllOfflineMatchCount(localName, cityName).enqueue(new Callback<GetAllOfflineMatchCountResponse>() {
                    @Override
                    public void onResponse(Call<GetAllOfflineMatchCountResponse> call, Response<GetAllOfflineMatchCountResponse> response) {
                        GetAllOfflineMatchCountResponse resp = response.body();
                        assert resp != null;
                        if (resp.getCode() == 1000) {
                            getAllOfflineMatchCountView.onGetAllOfflineMatchCountSuccess(resp);
                        } else {
                            getAllOfflineMatchCountView.onGetAllOfflineMatchCountFailure(resp);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllOfflineMatchCountResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<GetAllOfflineMatchCountResponse> call, Throwable t) {

            }
        });
    }

}
