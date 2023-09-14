package com.uou.capstoneproject.data.match.response.plan;

import com.google.gson.annotations.SerializedName;

public class GetRemainMatchRoomResultDetail {
    @SerializedName("game_time") String gameTime;
    @SerializedName("network_type") String networkType;
    @SerializedName("nickname") String nickName;
    @SerializedName("count") int count;
    @SerializedName("historyIdx") int historyIdx;
    @SerializedName("userIdx") int userIdx;
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("teamIdx") int teamIdx;
    @SerializedName("homeOrAway") String homeOrAway;
    @SerializedName("place") String place;
    @SerializedName("profile_imgurl") String imageUrl;

    public GetRemainMatchRoomResultDetail(String gameTime, String networkType, String nickName, int count, int historyIdx, int userIdx, int matchIdx, int teamIdx, String homeOrAway, String place, String imageUrl) {
        this.gameTime = gameTime;
        this.networkType = networkType;
        this.nickName = nickName;
        this.count = count;
        this.historyIdx = historyIdx;
        this.userIdx = userIdx;
        this.matchIdx = matchIdx;
        this.teamIdx = teamIdx;
        this.homeOrAway = homeOrAway;
        this.place = place;
        this.imageUrl = imageUrl;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHistoryIdx() {
        return historyIdx;
    }

    public void setHistoryIdx(int historyIdx) {
        this.historyIdx = historyIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public int getTeamIdx() {
        return teamIdx;
    }

    public void setTeamIdx(int teamIdx) {
        this.teamIdx = teamIdx;
    }

    public String getHomeOrAway() {
        return homeOrAway;
    }

    public void setHomeOrAway(String homeOrAway) {
        this.homeOrAway = homeOrAway;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
