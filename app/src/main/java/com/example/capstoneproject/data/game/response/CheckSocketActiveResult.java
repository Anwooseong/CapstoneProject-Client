package com.example.capstoneproject.data.game.response;

import com.google.gson.annotations.SerializedName;

public class CheckSocketActiveResult {
    @SerializedName(value = "status") private String status;

    public CheckSocketActiveResult(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
