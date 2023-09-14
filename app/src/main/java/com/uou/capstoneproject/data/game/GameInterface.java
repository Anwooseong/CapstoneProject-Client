package com.uou.capstoneproject.data.game;

import com.uou.capstoneproject.data.game.request.CheckSocketActiveRequest;
import com.uou.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.uou.capstoneproject.data.game.response.ChatRoomDTO;
import com.uou.capstoneproject.data.game.response.CheckSocketActiveResponse;
import com.uou.capstoneproject.data.game.request.PostGameEndRequest;
import com.uou.capstoneproject.data.game.response.PostGameEndResponse;
import com.uou.capstoneproject.data.game.response.PostMatchCodeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GameInterface {
    @POST("/app/game/open") // (클라이언트) 예정매치방 들어왔을때 roomIdx get하는 API
    Call<ChatRoomDTO> getMatchRoomID(@Query("id") String id);

    @POST("/app/game/join") // (클라이언트) 매칭시작 눌렀을때
    Call<CheckSocketActiveResponse> checkSocketActive(@Body CheckSocketActiveRequest checkSocketActiveRequest);

    @POST("/app/game/match-code") // (관리자) 매칭코드 서버에 보냄 -> roomIdx get해옴
    Call<PostMatchCodeResponse> postMatchCode(@Body PostMatchCodeRequest postMatchCodeRequest);

    @POST("/app/game/over")
    Call<PostGameEndResponse> postGameEnd(@Body List<PostGameEndRequest> postGameEndRequestList);
}
