package com.uou.capstoneproject.async.api.auth.email.request;

import com.google.gson.annotations.SerializedName;

public class PostAuthEmailReq {
    @SerializedName(value = "email")
    private String email;

    public PostAuthEmailReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PostAuthEmailBeforeReq{" +
                "email='" + email + '\'' +
                '}';
    }
}
