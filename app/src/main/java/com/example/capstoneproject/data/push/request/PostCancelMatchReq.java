package com.example.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostCancelMatchReq {

    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("userIdxList")
    List<Integer> userIdxList;

    public PostCancelMatchReq(int matchIdx, List<Integer> userIdxList) {
        this.matchIdx = matchIdx;
        this.userIdxList = userIdxList;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public List<Integer> getUserIdxList() {
        return userIdxList;
    }

    public void setUserIdxList(List<Integer> userIdxList) {
        this.userIdxList = userIdxList;
    }
}
