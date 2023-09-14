package com.uou.capstoneproject.data.game;

import com.uou.capstoneproject.data.NetworkModule;
import com.uou.capstoneproject.data.game.request.CheckSocketActiveRequest;
import com.uou.capstoneproject.data.game.request.PostGameEndRequest;
import com.uou.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.uou.capstoneproject.data.game.response.ChatRoomDTO;
import com.uou.capstoneproject.data.game.response.CheckSocketActiveResponse;
import com.uou.capstoneproject.data.game.response.PostGameEndResponse;
import com.uou.capstoneproject.data.game.response.PostMatchCodeResponse;
import com.uou.capstoneproject.view.CheckSocketActiveView;
import com.uou.capstoneproject.view.PostGameEndView;
import com.uou.capstoneproject.view.PostGameView;
import com.uou.capstoneproject.view.PostMatchCodeView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameService {
    private final GameInterface socketConnectInterface = NetworkModule.getRetrofit().create(GameInterface.class);
    private PostGameView postGameView;
    private PostMatchCodeView postMatchCodeView;
    private CheckSocketActiveView checkSocketActiveView;
    private PostGameEndView postGameEndView;

    public void setPostGameView(PostGameView postGameView) {
        this.postGameView = postGameView;
    }
    public void setPostMatchCodeView(PostMatchCodeView postMatchCodeView){
        this.postMatchCodeView = postMatchCodeView;
    }

    public void setCheckSocketActiveView(CheckSocketActiveView checkSocketActiveView) {
        this.checkSocketActiveView = checkSocketActiveView;
    }

    public void setPostGameEndView(PostGameEndView postGameEndView) {
        this.postGameEndView = postGameEndView;
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
    /**
     * 매칭 코드 전송 API
     * @param postMatchCodeRequest
     */
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

    /**
     * 소켓 활성화 여부 API
     * @param checkSocketActiveRequest
     */
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
    //POST

    /**
     * 게임 종료 API
     * @param postGameEndRequestList
     */
    public void postGameEnd(List<PostGameEndRequest> postGameEndRequestList){
        socketConnectInterface.postGameEnd(postGameEndRequestList).enqueue(new Callback<PostGameEndResponse>() {
            @Override
            public void onResponse(Call<PostGameEndResponse> call, Response<PostGameEndResponse> response) {
                PostGameEndResponse resp = response.body();
                assert resp != null;
                if(resp.getCode() == 1000){
                    postGameEndView.onPostGameSuccess(resp.getResult());
                }
            }

            @Override
            public void onFailure(Call<PostGameEndResponse> call, Throwable t) {

            }
        });
    }
}
