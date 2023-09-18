package com.uou.capstoneproject.async.api.auth.email.response.result;

import com.google.gson.annotations.SerializedName;

public class SignUpWithEmailResult {
    @SerializedName(value = "userIdx")
    private Long userIdx;

    public SignUpWithEmailResult(Long userIdx) {
        this.userIdx = userIdx;
    }

    public Long getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(Long userIdx) {
        this.userIdx = userIdx;
    }

    @Override
    public String toString() {
        return "SignUpWithEmailResult{" +
                "userIdx=" + userIdx +
                '}';
    }
}
