package com.uou.capstoneproject.data.match.response.plan;

import com.google.gson.annotations.SerializedName;

public class GetDetailMatchResultDetail {
    @SerializedName("userIdx") int userIdx;
    @SerializedName("nickName") String nickName;
    @SerializedName("profile_imgurl") String imageUrl;
    @SerializedName("highScore") int highScore;
    @SerializedName("avgScore") int avgScore;
    @SerializedName("gameCount") int gameCount;
    @SerializedName("winCount") int winCount;
    @SerializedName("loseCount") int loseCount;
    @SerializedName("homeOrAway") String homeOrAway;

    public GetDetailMatchResultDetail(int userIdx, String nickName, String imageUrl, int highScore, int avgScore, int gameCount, int winCount, int loseCount, String homeOrAway) {
        this.userIdx = userIdx;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.highScore = highScore;
        this.avgScore = avgScore;
        this.gameCount = gameCount;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.homeOrAway = homeOrAway;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public String getHomeOrAway() {
        return homeOrAway;
    }

    public void setHomeOrAway(String homeOrAway) {
        this.homeOrAway = homeOrAway;
    }
}
