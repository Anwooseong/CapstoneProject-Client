package com.uou.capstoneproject.async.api.auth.social.kakao.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.uou.capstoneproject.async.api.auth.social.kakao.response.result.KakaoLoginResult;

public class KakaoLoginResponse {
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;

    @SerializedName(value = "httpStatus")
    private String httpStatus;
    @Nullable
    @SerializedName(value = "result")
    private KakaoLoginResult result;

    public KakaoLoginResponse(int code, String message, String httpStatus, @Nullable KakaoLoginResult result) {
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

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Nullable
    public KakaoLoginResult getResult() {
        return result;
    }

    public void setResult(@Nullable KakaoLoginResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "KakaoLoginResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", httpStatus='" + httpStatus + '\'' +
                ", result=" + result +
                '}';
    }
}
