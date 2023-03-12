package com.example.capstoneproject.viewmodel;

public class OfflineRoomModel {
    private String imgUrl;
    private String category;
    private String date;
    private String place;
    private String avg;

    public OfflineRoomModel(String imgUrl, String category, String date, String place, String avg) {
        this.imgUrl = imgUrl;
        this.category = category;
        this.date = date;
        this.place = place;
        this.avg = avg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getAvg() {
        return avg;
    }
}
