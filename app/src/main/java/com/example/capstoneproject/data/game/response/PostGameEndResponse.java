package com.example.capstoneproject.data.game.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PostGameEndResponse {
    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private PostGameEndResult result;

    public PostGameEndResponse(boolean isSuccess, int code, String message, @Nullable PostGameEndResult result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public PostGameEndResult getResult() {
        return result;
    }

    public void setResult(@Nullable PostGameEndResult result) {
        this.result = result;
    }
}
