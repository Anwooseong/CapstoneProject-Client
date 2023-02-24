package com.example.capstoneproject.data.auth.response.result;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName(value = "userIdx") private int userIdx;
    @SerializedName(value = "jwt") private String jwt;
    @SerializedName(value = "name") private String name;
    @SerializedName(value = "nickname") private String nickName;

    public LoginResult(int userIdx, String jwt, String name, String nickName) {
        this.userIdx = userIdx;
        this.jwt = jwt;
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
