package com.example.capstoneproject.data.auth.response.login;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName(value = "jwt") private String jwt;
    @SerializedName(value = "userIdx") private int userIdx;
    @SerializedName(value = "name") private String name;
    @SerializedName(value = "nickname") private String nickName;

    public LoginResult(String jwt, int userIdx, String name, String nickName) {
        this.jwt = jwt;
        this.userIdx = userIdx;
        this.name = name;
        this.nickName = nickName;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
