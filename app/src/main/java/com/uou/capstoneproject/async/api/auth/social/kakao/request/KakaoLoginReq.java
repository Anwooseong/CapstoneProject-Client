package com.uou.capstoneproject.async.api.auth.social.kakao.request;

import com.google.gson.annotations.SerializedName;

public class KakaoLoginReq {
    @SerializedName(value = "profile_img")
    private String profileImg;
    @SerializedName(value = "email")
    private String email;
    @SerializedName(value = "nickname")
    private String nickname;
    @SerializedName(value = "provider")
    private String provider;

    public KakaoLoginReq(String profileImg, String email, String nickname, String provider) {
        this.profileImg = profileImg;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "KakaoLoginReq{" +
                "profileImg='" + profileImg + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
