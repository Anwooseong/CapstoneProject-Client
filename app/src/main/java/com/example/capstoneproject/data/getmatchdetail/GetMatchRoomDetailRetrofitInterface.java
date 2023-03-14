package com.example.capstoneproject.data.getmatchdetail;

import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResponse;
import com.example.capstoneproject.data.getmatchdetail.response.GetMatchRoomDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMatchRoomDetailRetrofitInterface {
    @GET("/app/matches/rooms/{matchIdx}")
    Call<GetMatchRoomDetailResponse> getMatchRoomDetail(@Path("matchIdx") int matchIdx);
}
