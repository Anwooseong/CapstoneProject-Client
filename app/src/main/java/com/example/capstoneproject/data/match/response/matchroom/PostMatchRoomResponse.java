package com.example.capstoneproject.data.match.response.matchroom;

import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

public class PostMatchRoomResponse {
    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private PostMatchRoomResult result;

    public PostMatchRoomResponse(boolean isSuccess, int code, String message, @Nullable PostMatchRoomResult result) {
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
    public PostMatchRoomResult getResult() {
        return result;
    }

    public void setResult(@Nullable PostMatchRoomResult result) {
        this.result = result;
    }
}
