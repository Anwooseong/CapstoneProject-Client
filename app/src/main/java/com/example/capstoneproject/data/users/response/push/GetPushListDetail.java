package com.example.capstoneproject.data.users.response.push;

import com.google.gson.annotations.SerializedName;

public class GetPushListDetail {
    @SerializedName("pushIdx") int pushIdx;
    @SerializedName("profileImg_url") String imageUrl;
    @SerializedName("opponentNick") String opponentNickName;
    @SerializedName("owner_userIdx") int ownerUserIdx;
    @SerializedName("join_userIdx") int joinUserIdx;
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("game_time") String gameTime;
    @SerializedName("network_type") String networkType;
    @SerializedName("onlydate") String onlyDate;
    @SerializedName("status") String status;

    public GetPushListDetail(int pushIdx, String imageUrl, String opponentNickName, int ownerUserIdx, int joinUserIdx, int matchIdx, String gameTime, String networkType, String onlyDate, String status) {
        this.pushIdx = pushIdx;
        this.imageUrl = imageUrl;
        this.opponentNickName = opponentNickName;
        this.ownerUserIdx = ownerUserIdx;
        this.joinUserIdx = joinUserIdx;
        this.matchIdx = matchIdx;
        this.gameTime = gameTime;
        this.networkType = networkType;
        this.onlyDate = onlyDate;
        this.status = status;
    }

    public int getPushIdx() {
        return pushIdx;
    }

    public void setPushIdx(int pushIdx) {
        this.pushIdx = pushIdx;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOpponentNickName() {
        return opponentNickName;
    }

    public void setOpponentNickName(String opponentNickName) {
        this.opponentNickName = opponentNickName;
    }

    public int getOwnerUserIdx() {
        return ownerUserIdx;
    }

    public void setOwnerUserIdx(int ownerUserIdx) {
        this.ownerUserIdx = ownerUserIdx;
    }

    public int getJoinUserIdx() {
        return joinUserIdx;
    }

    public void setJoinUserIdx(int joinUserIdx) {
        this.joinUserIdx = joinUserIdx;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(String onlyDate) {
        this.onlyDate = onlyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
