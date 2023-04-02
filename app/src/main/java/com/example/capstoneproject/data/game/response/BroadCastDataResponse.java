package com.example.capstoneproject.data.game.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BroadCastDataResponse {
    @SerializedName(value = "matchIdx") private String matchIdx;
    @SerializedName(value = "writer") private String writer;
    @SerializedName(value = "frame") private int frame;
    @SerializedName(value = "score") private int score;

    public BroadCastDataResponse(String matchIdx, String writer, int frame, int score) {
        this.matchIdx = matchIdx;
        this.writer = writer;
        this.frame = frame;
        this.score = score;
    }

    public String getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(String matchIdx) {
        this.matchIdx = matchIdx;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
