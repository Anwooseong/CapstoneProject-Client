package com.example.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

public class PostGameEndResult {
    @SerializedName(value = "historyIdx") private int historyIdx;

    public PostGameEndResult(int historyIdx) {
        this.historyIdx = historyIdx;
    }

    public int getHistoryIdx() {
        return historyIdx;
    }

    public void setHistoryIdx(int historyIdx) {
        this.historyIdx = historyIdx;
    }
}
