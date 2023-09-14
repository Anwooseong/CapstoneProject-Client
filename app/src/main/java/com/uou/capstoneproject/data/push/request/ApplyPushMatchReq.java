package com.uou.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

public class ApplyPushMatchReq {
    @SerializedName("matchOwnerUserIdx") int matchOwnerUserIdx;
    @SerializedName("matchIdx") int matchIdx;

    public ApplyPushMatchReq(int matchOwnerUserIdx, int matchIdx) {
        this.matchOwnerUserIdx = matchOwnerUserIdx;
        this.matchIdx = matchIdx;
    }

    public int getMatchOwnerUserIdx() {
        return matchOwnerUserIdx;
    }

    public void setMatchOwnerUserIdx(int matchOwnerUserIdx) {
        this.matchOwnerUserIdx = matchOwnerUserIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    @Override
    public String toString() {
        return "ApplyPushMatchReq{" +
                "matchOwnerUserIdx=" + matchOwnerUserIdx +
                ", matchIdx=" + matchIdx +
                '}';
    }
}
