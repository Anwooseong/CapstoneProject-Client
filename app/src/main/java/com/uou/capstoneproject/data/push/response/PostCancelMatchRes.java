package com.uou.capstoneproject.data.push.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PostCancelMatchRes {

    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private Integer result;

    public PostCancelMatchRes(boolean isSuccess, int code, String message, @Nullable Integer result) {
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
    public Integer getResult() {
        return result;
    }

    public void setResult(@Nullable Integer result) {
        this.result = result;
    }
}
