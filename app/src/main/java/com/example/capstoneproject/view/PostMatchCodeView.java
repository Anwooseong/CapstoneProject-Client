package com.example.capstoneproject.view;

import com.example.capstoneproject.data.game.response.PostMatchCodeResult;

public interface PostMatchCodeView {
    void onPostMatchCodeSuccess(PostMatchCodeResult result);
    void onPostMatchCodeFailure();
}
