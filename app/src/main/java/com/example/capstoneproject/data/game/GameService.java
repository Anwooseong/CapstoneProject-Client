package com.example.capstoneproject.data.game;

import com.example.capstoneproject.data.NetworkModule;
import com.example.capstoneproject.data.game.request.CheckSocketActiveRequest;
import com.example.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.example.capstoneproject.data.game.response.ChatRoomDTO;
import com.example.capstoneproject.data.game.response.CheckSocketActiveResponse;
import com.example.capstoneproject.data.game.response.PostMatchCodeResponse;
import com.example.capstoneproject.view.CheckSocketActiveView;
import com.example.capstoneproject.view.PostGameView;
import com.example.capstoneproject.view.PostMatchCodeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameService {
    private final GameInterface socketConnectInterface = NetworkModule.getRetrofit().create(GameInterface.class);
    private PostGameView postGameView;
    private PostMatchCodeView postMatchCodeView;
    private CheckSocketActiveView checkSocketActiveView;

    public void setPostGameView(PostGameView postGameView) {
        this.postGameView = postGameView;
    }
    public void setPostMatchCodeView(PostMatchCodeView postMatchCodeView){
        this.postMatchCodeView = postMatchCodeView;
    }

    public void setCheckSocketActiveView(CheckSocketActiveView checkSocketActiveView) {
        this.checkSocketActiveView = checkSocketActiveView;
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
    // POST
    public void postMatchCode(PostMatchCodeRequest postMatchCodeRequest){
        socketConnectInterface.postMatchCode(postMatchCodeRequest).enqueue(new Callback<PostMatchCodeResponse>() {
            @Override
            public void onResponse(Call<PostMatchCodeResponse> call, Response<PostMatchCodeResponse> response) {
                PostMatchCodeResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    postMatchCodeView.onPostMatchCodeSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<PostMatchCodeResponse> call, Throwable t) {

            }
        });
    }
    //POST
    public void checkSocketActive(CheckSocketActiveRequest checkSocketActiveRequest) {
        socketConnectInterface.checkSocketActive(checkSocketActiveRequest).enqueue(new Callback<CheckSocketActiveResponse>() {
            @Override
            public void onResponse(Call<CheckSocketActiveResponse> call, Response<CheckSocketActiveResponse> response) {
                CheckSocketActiveResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    checkSocketActiveView.onCheckSocketActiveViewSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<CheckSocketActiveResponse> call, Throwable t) {

            }
        });
    }
}
