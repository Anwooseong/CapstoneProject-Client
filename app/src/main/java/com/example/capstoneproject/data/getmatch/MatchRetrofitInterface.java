package com.example.capstoneproject.data.getmatch;

import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchRetrofitInterface {
    @GET("/app/matches/rooms")
    Call<GetMatchRoomResponse> getMatchRoom(@Query("network") String network);

}
