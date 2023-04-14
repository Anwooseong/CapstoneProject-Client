package com.example.capstoneproject.data.push.request;

import com.google.gson.annotations.SerializedName;

public class PostCancelMatchUser {
    @SerializedName(value = "userIdx") private int userIdx;

    public PostCancelMatchUser(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }
}
