package com.uou.capstoneproject.data.game.request;

import com.google.gson.annotations.SerializedName;

public class PostGameEndRequest {
    @SerializedName(value = "historyIdx") private int historyIdx;
    @SerializedName(value = "frameScoresPerPitch") private int[][] frameScoresPerPitch;
    @SerializedName(value = "frameScores") private int [] frameScores;

    public PostGameEndRequest(int historyIdx, int[][] frameScoresPerPitch, int[] frameScores) {
        this.historyIdx = historyIdx;
        this.frameScoresPerPitch = frameScoresPerPitch;
        this.frameScores = frameScores;
    }

    public int getHistoryIdx() {
        return historyIdx;
    }

    public void setHistoryIdx(int historyIdx) {
        this.historyIdx = historyIdx;
    }

    public int[][] getFrameScoresPerPitch() {
        return frameScoresPerPitch;
    }

    public void setFrameScoresPerPitch(int[][] frameScoresPerPitch) {
        this.frameScoresPerPitch = frameScoresPerPitch;
    }

    public int[] getFrameScores() {
        return frameScores;
    }

    public void setFrameScores(int[] frameScores) {
        this.frameScores = frameScores;
    }
}
