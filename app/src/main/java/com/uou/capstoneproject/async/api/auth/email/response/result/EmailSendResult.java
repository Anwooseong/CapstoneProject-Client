package com.uou.capstoneproject.async.api.auth.email.response.result;

import com.google.gson.annotations.SerializedName;

public class EmailSendResult {
    @SerializedName(value = "authCode")
    private String authCode;

    public EmailSendResult(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "EmailSendResult{" +
                "authEmailCheck='" + authCode + '\'' +
                '}';
    }
}
