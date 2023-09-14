package com.uou.capstoneproject.data.users.response.push;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPushListResult {
    @SerializedName("date") String date;
    @SerializedName("alarmDetail") List<GetPushListDetail> alarmDetailList;

    public GetPushListResult(String date, List<GetPushListDetail> alarmDetailList) {
        this.date = date;
        this.alarmDetailList = alarmDetailList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<GetPushListDetail> getAlarmDetailList() {
        return alarmDetailList;
    }

    public void setAlarmDetailList(List<GetPushListDetail> alarmDetailList) {
        this.alarmDetailList = alarmDetailList;
    }
}
