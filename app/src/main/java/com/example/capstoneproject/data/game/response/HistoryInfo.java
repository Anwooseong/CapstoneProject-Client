package com.example.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

public class HistoryInfo {
    @SerializedName(value = "historyIdx") private int historyIdx;
    @SerializedName(value = "nickName") private String nickName;

    public HistoryInfo(int historyIdx, String nickName) {
        this.historyIdx = historyIdx;
        this.nickName = nickName;
    }

    public int getHistoryIdx() {
        return historyIdx;
    }

    public void setHistoryIdx(int historyIdx) {
        this.historyIdx = historyIdx;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
