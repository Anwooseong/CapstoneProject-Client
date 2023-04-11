package com.example.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

public class PostCancelMatchReq {

    @SerializedName("matchIdx") int matchIdx;

    public PostCancelMatchReq(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }
}
