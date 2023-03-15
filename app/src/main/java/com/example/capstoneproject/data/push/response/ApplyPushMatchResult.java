package com.example.capstoneproject.data.push.response;

import com.google.gson.annotations.SerializedName;

public class ApplyPushMatchResult {
    @SerializedName("pushIdx") int pushIdx;

    public ApplyPushMatchResult(int pushIdx) {
        this.pushIdx = pushIdx;
    }

    public int getPushIdx() {
        return pushIdx;
    }

    public void setPushIdx(int pushIdx) {
        this.pushIdx = pushIdx;
    }
}
