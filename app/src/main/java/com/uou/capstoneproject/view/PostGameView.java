package com.uou.capstoneproject.view;


import com.uou.capstoneproject.data.game.response.ChatRoomDTO;

public interface PostGameView {
    void onPostGameSuccess(ChatRoomDTO result);
    void onPostGameFailure();
}
