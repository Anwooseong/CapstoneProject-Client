package com.example.capstoneproject.data.users.response.info;

import com.google.gson.annotations.SerializedName;

public class GetUserInfoResult {
    @SerializedName("name")private String name;
    @SerializedName("nickName")private String nickName;
    @SerializedName("avg")private int average;
    @SerializedName("winCount")private int winCount;
    @SerializedName("loseCount")private int loseCount;
    @SerializedName("drawCount")private int drawCount;
    @SerializedName("highScore")private int highScore;
    @SerializedName("strikeRate")private double strikeRate;
    @SerializedName("gameCount")private int gameCount;

    public GetUserInfoResult(String name, String nickName, int average, int winCount, int loseCount, int drawCount, int highScore, double strikeRate, int gameCount) {
        this.name = name;
        this.nickName = nickName;
        this.average = average;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.drawCount = drawCount;
        this.highScore = highScore;
        this.strikeRate = strikeRate;
        this.gameCount = gameCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
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

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(double strikeRate) {
        this.strikeRate = strikeRate;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
