package com.example.capstoneproject.view;


import com.example.capstoneproject.data.game.response.ChatRoomDTO;

import java.util.List;

public interface PostGameView {
    void onPostGameSuccess(ChatRoomDTO result);
    void onPostGameFailure();
}
