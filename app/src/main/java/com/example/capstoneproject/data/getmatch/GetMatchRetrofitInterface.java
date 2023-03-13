package com.example.capstoneproject.data.getmatch;

import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMatchRetrofitInterface {
    @GET("/app/matches/rooms")
    Call<GetMatchRoomResponse> getMatchRoom(@Query("network") String network);
}
