package com.uou.capstoneproject.data.match.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GetMatchRoomDetailResult {
    @SerializedName("date") String date;
    @SerializedName("nickname") String nickname;
    @SerializedName("title") String title;
    @SerializedName("content") String content;
    @SerializedName("count") int count;
    @SerializedName("targetScore") int targetScore;
    @SerializedName("cost") int cost;
    @Nullable @SerializedName("location") String location;
    @Nullable @SerializedName("place") String place;
    @SerializedName("matchOwnerUserIdx") int matchUserIdx;

    public GetMatchRoomDetailResult(String date, String nickname, String title, String content, int count, int targetScore, int cost, @Nullable String location, @Nullable String place, int matchUserIdx) {
        this.date = date;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.count = count;
        this.targetScore = targetScore;
        this.cost = cost;
        this.location = location;
        this.place = place;
        this.matchUserIdx = matchUserIdx;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    public void setLocation(@Nullable String location) {
        this.location = location;
    }

    @Nullable
    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }

    public int getMatchUserIdx() {
        return matchUserIdx;
    }

    public void setMatchUserIdx(int matchUserIdx) {
        this.matchUserIdx = matchUserIdx;
    }
}
