package com.example.capstoneproject.data.users.response;

import androidx.annotation.Nullable;

import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRecordResponse {
    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private List<GetRecordResult> result;

    public GetRecordResponse(boolean isSuccess, int code, String message, @Nullable List<GetRecordResult> result) {
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
    public List<GetRecordResult> getResult() {
        return result;
    }

    public void setResult(@Nullable List<GetRecordResult> result) {
        this.result = result;
    }
}
