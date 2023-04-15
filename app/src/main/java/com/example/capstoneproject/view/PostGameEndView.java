package com.example.capstoneproject.view;

import com.example.capstoneproject.data.game.response.PostGameEndResult;

public interface PostGameEndView {
    void onPostGameSuccess(PostGameEndResult postGameEndResult);
    void onPostGameFailure();
}
