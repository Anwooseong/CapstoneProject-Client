package com.uou.capstoneproject.data.match.response.matchroom;

import com.google.gson.annotations.SerializedName;

public class PostMatchRoomResult {
    @SerializedName(value = "matchIdx") private int matchIdx;

    public PostMatchRoomResult(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }
}
