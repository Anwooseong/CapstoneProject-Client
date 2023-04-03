package com.example.capstoneproject.data.match.response.plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRemainMatchRoomResult {
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("game_time") String gameTime;
    @SerializedName("network_type") String networkType;
    @SerializedName("getMatchPlanResList") List<GetRemainMatchRoomResultDetail> getRemainMatchRoomResultDetailList;

    public GetRemainMatchRoomResult(int matchIdx, String gameTime, String networkType, List<GetRemainMatchRoomResultDetail> getRemainMatchRoomResultDetailList) {
        this.matchIdx = matchIdx;
        this.gameTime = gameTime;
        this.networkType = networkType;
        this.getRemainMatchRoomResultDetailList = getRemainMatchRoomResultDetailList;
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

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public List<GetRemainMatchRoomResultDetail> getGetRemainMatchRoomResultDetailList() {
        return getRemainMatchRoomResultDetailList;
    }

    public void setGetRemainMatchRoomResultDetailList(List<GetRemainMatchRoomResultDetail> getRemainMatchRoomResultDetailList) {
        this.getRemainMatchRoomResultDetailList = getRemainMatchRoomResultDetailList;
    }
}
