package com.example.capstoneproject.data.users.response.record;

import com.google.gson.annotations.SerializedName;

public class MatchRecordRes {
    @SerializedName("date") String date;
    @SerializedName("nickname") String nickname;
    @SerializedName("network_type") String networkType;
    @SerializedName("count") int count;
    @SerializedName("userIdx") int userIdx;
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("team") int team;
    @SerializedName("homeOrAway") String homeOrAway;
    @SerializedName("winOrLose") String winOrLose;
    @SerializedName("total_score") int totalScore;

    public MatchRecordRes(String date, String nickname, String networkType, int count, int userIdx, int matchIdx, int team, String homeOrAway, String winOrLose, int totalScore) {
        this.date = date;
        this.nickname = nickname;
        this.networkType = networkType;
        this.count = count;
        this.userIdx = userIdx;
        this.matchIdx = matchIdx;
        this.team = team;
        this.homeOrAway = homeOrAway;
        this.winOrLose = winOrLose;
        this.totalScore = totalScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getHomeOrAway() {
        return homeOrAway;
    }

    public void setHomeOrAway(String homeOrAway) {
        this.homeOrAway = homeOrAway;
    }

    public String getWinOrLose() {
        return winOrLose;
    }

    public void setWinOrLose(String winOrLose) {
        this.winOrLose = winOrLose;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
