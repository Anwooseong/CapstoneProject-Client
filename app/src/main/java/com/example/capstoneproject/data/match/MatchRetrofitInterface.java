package com.example.capstoneproject.data.match;

import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MatchRetrofitInterface {
    @POST("/app/matches/rooms")
    Call<PostMatchRoomResponse> createMatchRoom(@Header("X-ACCESS-TOKEN") String jwt,
                                                @Body PostMatchRoom postMatchRoom);

    @GET("/app/matches/rooms/plans")
    Call<GetRemainMatchRoomResponse> getRemainingMatchRoom(@Header("X-ACCESS-TOKEN") String jwt);
}
