package com.uou.capstoneproject.data.auth.request;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName(value = "uid")
    private String id;
    @SerializedName(value = "password")
    private String password;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "nickName")
    private String nickname;
    @SerializedName(value = "token")
    private String token;

    //회원가입 생성자
    public User(String id, String password, String name, String nickname, String token) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.token = token;
    }

    //로그인 생성자
    public User(String id, String password, String token) {
        this.id = id;
        this.password = password;
        this.token = token;
    }

    //중복확인 생성자
    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}