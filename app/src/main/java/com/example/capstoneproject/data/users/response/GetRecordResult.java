package com.example.capstoneproject.data.users.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRecordResult {
    @SerializedName("matchIdx") int matchIdx;
    @SerializedName("matchRecordsResList") List<MatchRecordRes> matchRecordsResList;

    public GetRecordResult(int matchIdx, List<MatchRecordRes> matchRecordsResList) {
        this.matchIdx = matchIdx;
        this.matchRecordsResList = matchRecordsResList;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public List<MatchRecordRes> getMatchRecordsResList() {
        return matchRecordsResList;
    }

    public void setMatchRecordsResList(List<MatchRecordRes> matchRecordsResList) {
        this.matchRecordsResList = matchRecordsResList;
    }
}
