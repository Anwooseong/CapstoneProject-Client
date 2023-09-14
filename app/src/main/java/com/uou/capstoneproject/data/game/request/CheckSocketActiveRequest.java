package com.uou.capstoneproject.data.game.request;

import com.google.gson.annotations.SerializedName;

public class CheckSocketActiveRequest {
    @SerializedName(value = "matchIdx") private int matchIdx;

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public CheckSocketActiveRequest(int matchIdx) {
        this.matchIdx = matchIdx;
    }
}
