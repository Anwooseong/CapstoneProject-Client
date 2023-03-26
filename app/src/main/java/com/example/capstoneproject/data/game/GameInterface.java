package com.example.capstoneproject.data.game;

import com.example.capstoneproject.data.game.response.ChatRoomDTO;
import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GameInterface {
    @POST("/app/game/open")
    Call<ChatRoomDTO> getMatchRoomID(@Query("id") String id);
}
