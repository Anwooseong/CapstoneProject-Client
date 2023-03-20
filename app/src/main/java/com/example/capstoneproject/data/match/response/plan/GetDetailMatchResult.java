package com.example.capstoneproject.data.match.response.plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDetailMatchResult {
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("gameTime") String gameTime;
    @SerializedName("matchCode") String matchCode;
    @SerializedName("getMatchPlanDetailRes") List<GetDetailMatchResultDetail> getDetailMatchResultDetails;

    public GetDetailMatchResult(int matchIdx, String gameTime, String matchCode, List<GetDetailMatchResultDetail> getDetailMatchResultDetails) {
        this.matchIdx = matchIdx;
        this.gameTime = gameTime;
        this.matchCode = matchCode;
        this.getDetailMatchResultDetails = getDetailMatchResultDetails;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public List<GetDetailMatchResultDetail> getGetDetailMatchResultDetails() {
        return getDetailMatchResultDetails;
    }

    public void setGetDetailMatchResultDetails(List<GetDetailMatchResultDetail> getDetailMatchResultDetails) {
        this.getDetailMatchResultDetails = getDetailMatchResultDetails;
    }
}
