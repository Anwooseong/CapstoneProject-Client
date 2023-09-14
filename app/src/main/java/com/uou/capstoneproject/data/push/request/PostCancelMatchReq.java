package com.uou.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostCancelMatchReq {

    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("matchCancelUserList")
    List<PostCancelMatchUser> userIdxList;

    public PostCancelMatchReq(int matchIdx, List<PostCancelMatchUser> userIdxList) {
        this.matchIdx = matchIdx;
        this.userIdxList = userIdxList;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public List<PostCancelMatchUser> getUserIdxList() {
        return userIdxList;
    }

    public void setUserIdxList(List<PostCancelMatchUser> userIdxList) {
        this.userIdxList = userIdxList;
    }

    @Override
    public String toString() {
        return "PostCancelMatchReq{" +
                "matchIdx=" + matchIdx +
                ", userIdxList=" + userIdxList +
                '}';
    }
}
