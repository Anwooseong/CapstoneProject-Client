package com.example.capstoneproject.data.push.response;

import com.google.gson.annotations.SerializedName;

public class ApplyPushMatchResult {
    @SerializedName("pushIdx") int pushIdx; // 매칭방 신청불가 시 0 , 신청가능 시 DB에 푸시key insert 후 PK 반환

    public ApplyPushMatchResult(int pushIdx) {
        this.pushIdx = pushIdx;
    }

    public int getPushIdx() {
        return pushIdx;
    }

    public void setPushIdx(int pushIdx) {
        this.pushIdx = pushIdx;
    }
}
