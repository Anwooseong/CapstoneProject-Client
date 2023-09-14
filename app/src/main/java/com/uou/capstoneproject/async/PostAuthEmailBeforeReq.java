package com.uou.capstoneproject.async;

import com.google.gson.annotations.SerializedName;

public class PostAuthEmailBeforeReq {
    @SerializedName(value = "email")
    private String email;

    public PostAuthEmailBeforeReq(String email) {
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
