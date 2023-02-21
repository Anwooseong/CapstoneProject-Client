package com.example.capstoneproject.data;

public class OnlineRoomModel {
    private String imgUrl;
    private String date;
    private String avg;

    public OnlineRoomModel(String imgUrl, String date, String avg) {
        this.imgUrl = imgUrl;
        this.date = date;
        this.avg = avg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDate() {
        return date;
    }

    public String getAvg() {
        return avg;
    }
}
