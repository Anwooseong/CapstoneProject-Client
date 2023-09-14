package com.uou.capstoneproject.data.push.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PostAcceptMatchRes {
    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private PostAcceptMatchRes result;

    public PostAcceptMatchRes(boolean isSuccess, int code, String message, @Nullable PostAcceptMatchRes result) {
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
    public PostAcceptMatchRes getResult() {
        return result;
    }

    public void setResult(@Nullable PostAcceptMatchRes result) {
        this.result = result;
    }
}
