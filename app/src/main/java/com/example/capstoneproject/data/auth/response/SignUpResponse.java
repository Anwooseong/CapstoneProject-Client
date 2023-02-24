package com.example.capstoneproject.data.auth.response;

import androidx.annotation.Nullable;

import com.example.capstoneproject.data.auth.response.result.SignUpResult;
import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private SignUpResult result;

    public SignUpResponse(boolean isSuccess, int code, String message, @Nullable SignUpResult result) {
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
    public SignUpResult getResult() {
        return result;
    }

    public void setResult(@Nullable SignUpResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "isSuccess=" + isSuccess +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
