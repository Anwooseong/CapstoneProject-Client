package com.example.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

public class PostMatchCodeResult {
    @SerializedName(value = "roomIdx") private int roomIdx; // 게임방Idx

    public PostMatchCodeResult(int roomIdx) {
        this.roomIdx = roomIdx;
    }

    public int getRoomIdx() {
        return roomIdx;
    }

    public void setRoomIdx(int roomIdx) {
        this.roomIdx = roomIdx;
    }
}
