package com.example.capstoneproject.viewmodel;

public class OnlineRoomModel {
    private int matchIdx;
    private String imgUrl;
    private String date;
    private int avg;
    private String place;
    private int numbers;

    public OnlineRoomModel(int matchIdx, String imgUrl, String date, int avg, String place, int numbers) {
        this.matchIdx = matchIdx;
        this.imgUrl = imgUrl;
        this.date = date;
        this.avg = avg;
        this.place = place;
        this.numbers = numbers;
    }

    public int getMatchIdx() {
        return matchIdx;
    }

    public void setMatchIdx(int matchIdx) {
        this.matchIdx = matchIdx;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
