package com.uou.capstoneproject.data.users.response.push;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPushListResponse {

    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private List<GetPushListResult> result;

    public GetPushListResponse(boolean isSuccess, int code, String message, @Nullable List<GetPushListResult> result) {
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
    public List<GetPushListResult> getResult() {
        return result;
    }

    public void setResult(@Nullable List<GetPushListResult> result) {
        this.result = result;
    }
}
