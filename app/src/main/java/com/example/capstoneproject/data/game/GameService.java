package com.example.capstoneproject.data.game;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.game.response.ChatRoomDTO;
import com.example.capstoneproject.view.PostGameView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameService {
    private final GameInterface socketConnectInterface = NetworkModule.getRetrofit().create(GameInterface.class);
    private PostGameView postGameView;

    public void setPostGameView(PostGameView postGameView) {
        this.postGameView = postGameView;
    }

    //POST
    public void postGame(String roomId) {
        socketConnectInterface.getMatchRoomID(roomId).enqueue(new Callback<ChatRoomDTO>() {
            @Override
            public void onResponse(Call<ChatRoomDTO> call, Response<ChatRoomDTO> response) {
                ChatRoomDTO resp = response.body();
                assert resp!= null;
                if(resp.getCode() == 1000){
                    postGameView.onPostGameSuccess(resp);
                }
            }

            @Override
            public void onFailure(Call<ChatRoomDTO> call, Throwable t) {

            }
        });
    }
}
