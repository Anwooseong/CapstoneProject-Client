package com.uou.capstoneproject.data.match.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GetMatchRoomResult {
    @SerializedName(value = "matchIdx") private int matchIdx;
    @SerializedName(value = "date") private String date;
    @SerializedName(value = "average") private int average;
    @Nullable @SerializedName(value = "place") private String place;
    @SerializedName(value = "numbers") private int numbers;

    public GetMatchRoomResult(int matchIdx, String date, int average, @Nullable String place, int numbers) {
        this.matchIdx = matchIdx;
        this.date = date;
        this.average = average;
        this.place = place;
        this.numbers = numbers;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    @Nullable
    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
