package com.example.capstoneproject.data.users.response.push;

import com.google.gson.annotations.SerializedName;

public class GetPushListDetail {
    @SerializedName("profileImg_url") String imageUrl;
    @SerializedName("owner_userIdx") int ownerUserIdx;
    @SerializedName("join_userIdx") int joinUserIdx;
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("push_title") String pushTitle;
    @SerializedName("push_content") String pushContent;
    @SerializedName("create") String createAt;
    @SerializedName("updated") String updateAt;
    @SerializedName("status") String status;

    public GetPushListDetail(String imageUrl, int ownerUserIdx, int joinUserIdx, int matchIdx, String pushTitle, String pushContent, String createAt, String updateAt, String status) {
        this.imageUrl = imageUrl;
        this.ownerUserIdx = ownerUserIdx;
        this.joinUserIdx = joinUserIdx;
        this.matchIdx = matchIdx;
        this.pushTitle = pushTitle;
        this.pushContent = pushContent;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
