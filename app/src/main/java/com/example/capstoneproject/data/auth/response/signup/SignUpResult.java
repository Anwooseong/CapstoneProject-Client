package com.example.capstoneproject.data.auth.response.signup;

import com.google.gson.annotations.SerializedName;

public class SignUpResult {
    @SerializedName(value = "userIdx") private int userIdx;

    public SignUpResult(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }
}
