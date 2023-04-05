package com.example.capstoneproject.view;

import com.example.capstoneproject.data.game.response.CheckSocketActiveResult;

public interface CheckSocketActiveView {
    void onCheckSocketActiveViewSuccess(CheckSocketActiveResult result);
    void onCheckSocketActiveViewFailure();
}
