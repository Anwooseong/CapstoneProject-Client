package com.uou.capstoneproject.async.api.auth.email.request;

import com.google.gson.annotations.SerializedName;

public class PostSignUpWithEmailReq {
    @SerializedName(value = "email")
    private String email;
    @SerializedName(value = "password")
    private String password;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "nickname")
    private String nickname;

    public PostSignUpWithEmailReq(String email, String password, String name, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "PostSignUpwithEmail{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
