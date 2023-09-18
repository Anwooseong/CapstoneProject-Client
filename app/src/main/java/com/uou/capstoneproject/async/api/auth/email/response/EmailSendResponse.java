package com.uou.capstoneproject.async.api.auth.email.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.uou.capstoneproject.async.api.auth.email.response.result.EmailSendResult;

public class EmailSendResponse {
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @Nullable
    @SerializedName(value = "result")
    private EmailSendResult result;

    public EmailSendResponse(int code, String message, @Nullable EmailSendResult result) {
        this.code = code;
        this.message = message;
        this.result = result;
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
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
