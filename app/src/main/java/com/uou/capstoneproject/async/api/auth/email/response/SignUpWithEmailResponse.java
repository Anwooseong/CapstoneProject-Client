package com.uou.capstoneproject.async.api.auth.email.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.uou.capstoneproject.async.api.auth.email.response.result.SignUpWithEmailResult;

public class SignUpWithEmailResponse {
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @SerializedName(value = "httpStatus")
    private String httpStatus;
    @Nullable
    @SerializedName(value = "result")
    private SignUpWithEmailResult result;

    public SignUpWithEmailResponse(int code, String message, String httpStatus,@Nullable SignUpWithEmailResult result) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
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
    public SignUpWithEmailResult getResult() {
        return result;
    }

    public void setResult(@Nullable SignUpWithEmailResult result) {
        this.result = result;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "SignUpWithEmailResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", httpStatus='" + httpStatus + '\'' +
                '}';
    }
}
