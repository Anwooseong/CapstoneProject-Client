package com.example.capstoneproject.data.match;

import com.example.capstoneproject.data.match.response.GetMatchCityResponse;
import com.example.capstoneproject.data.match.response.GetMatchRoomDetailResponse;
import com.example.capstoneproject.data.match.response.GetMatchRoomResponse;
import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MatchRetrofitInterface {
    @POST("/app/matches/rooms")
    Call<PostMatchRoomResponse> createMatchRoom(@Header("X-ACCESS-TOKEN") String jwt,
                                                @Body PostMatchRoom postMatchRoom);

    @GET("/app/matches/rooms/plans")
    Call<GetRemainMatchRoomResponse> getRemainingMatchRoom(@Header("X-ACCESS-TOKEN") String jwt);

    @GET("/app/matches/rooms/plans/{matchIdx}")
    Call<GetDetailMatchResponse> getDetailMatch(@Header("X-ACCESS-TOKEN") String jwt, @Path("matchIdx") int matchIdx);

    @GET("/app/matches/rooms")
    Call<GetMatchRoomResponse> getMatchRoom(@Query("network") String network, @Query("localName") String local, @Query("cityName") String city);

    @GET("/app/matches/rooms/{matchIdx}")
    Call<GetMatchRoomDetailResponse> getMatchRoomDetail(@Path("matchIdx") int matchIdx);

    @GET("/app/matches/local")
    Call<GetMatchCityResponse> getMatchCity(@Query("localName") String local);
}
