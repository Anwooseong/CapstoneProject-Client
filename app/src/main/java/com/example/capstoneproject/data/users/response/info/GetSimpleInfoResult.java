package com.example.capstoneproject.data.users.response.info;

import com.google.gson.annotations.SerializedName;

public class GetSimpleInfoResult {
    @SerializedName("name")private String name;
    @SerializedName("nickname")private String nickName;
    @SerializedName("average")private int average;
    @SerializedName("winCount")private int winCount;
    @SerializedName("loseCount")private int loseCount;
    @SerializedName("winRate")private int winLate;

    public GetSimpleInfoResult(String name, String nickName, int average, int winCount, int loseCount, int winLate) {
        this.name = name;
        this.nickName = nickName;
        this.average = average;
        this.winCount = winCount;
        this.loseCount = loseCount;
        this.winLate = winLate;
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

    public int getWinLate() {
        return winLate;
    }

    public void setWinLate(int winLate) {
        this.winLate = winLate;
    }

    @Override
    public String toString() {
        return "GetSimpleInfoResult{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", average=" + average +
                ", winCount=" + winCount +
                ", loseCount=" + loseCount +
                ", winLate=" + winLate +
                '}';
    }
}
