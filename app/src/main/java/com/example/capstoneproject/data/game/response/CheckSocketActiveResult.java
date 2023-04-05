package com.example.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckSocketActiveResult {
    @SerializedName(value = "status") private String status;
    @SerializedName(value = "historyInfo") private List<HistoryInfo> historyInfo;

    public CheckSocketActiveResult(String status, List<HistoryInfo> historyInfo) {
        this.status = status;
        this.historyInfo = historyInfo;
    }

    public List<HistoryInfo> getHistoryInfo() {
        return historyInfo;
    }

    public void setHistoryInfo(List<HistoryInfo> historyInfo) {
        this.historyInfo = historyInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
