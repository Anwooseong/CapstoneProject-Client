package com.uou.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

public class PostAcceptMatchReq {
    @SerializedName("pushIdx") int pushIdx;
    @SerializedName("owner_userIdx") int ownerUserIdx;
    @SerializedName("join_userIdx") int joinUserIdx;
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("accept") boolean accept;

    public PostAcceptMatchReq(int pushIdx, int ownerUserIdx, int joinUserIdx, int matchIdx, boolean accept) {
        this.pushIdx = pushIdx;
        this.ownerUserIdx = ownerUserIdx;
        this.joinUserIdx = joinUserIdx;
        this.matchIdx = matchIdx;
        this.accept = accept;
    }

    public int getPushIdx() {
        return pushIdx;
    }

    public void setPushIdx(int pushIdx) {
        this.pushIdx = pushIdx;
    }

    public int getOwnerUserIdx() {
        return ownerUserIdx;
    }

    public void setOwnerUserIdx(int ownerUserIdx) {
        this.ownerUserIdx = ownerUserIdx;
    }

    public int getJoinUserIdx() {
        return joinUserIdx;
    }

    public void setJoinUserIdx(int joinUserIdx) {
        this.joinUserIdx = joinUserIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
