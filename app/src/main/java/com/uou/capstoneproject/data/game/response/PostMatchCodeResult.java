package com.uou.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostMatchCodeResult {
    @SerializedName(value = "roomIdx") private int roomIdx; // 게임방Idx
    @SerializedName(value = "historyInfo") private List<HistoryInfo> historyInfo;

    public PostMatchCodeResult(int roomIdx, List<HistoryInfo> historyInfo) {
        this.roomIdx = roomIdx;
        this.historyInfo = historyInfo;
    }

    public List<HistoryInfo> getHistoryInfo() {
        return historyInfo;
    }

    public void setHistoryInfo(List<HistoryInfo> historyInfo) {
        this.historyInfo = historyInfo;
    }

    public int getRoomIdx() {
        return roomIdx;
    }

    public void setRoomIdx(int roomIdx) {
        this.roomIdx = roomIdx;
    }
}
