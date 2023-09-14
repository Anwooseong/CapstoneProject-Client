package com.uou.capstoneproject.async;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EmailSendResponse {
    @SerializedName(value = "isSuccess") private boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private EmailSendResult result;

    public EmailSendResponse(boolean isSuccess, int code, String message, @Nullable EmailSendResult result) {
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
    public EmailSendResult getResult() {
        return result;
    }

    public void setResult(@Nullable EmailSendResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EmailSendResponse{" +
                "isSuccess=" + isSuccess +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
