package com.example.capstoneproject.data;

public class NextMatchModel {
    private String date;
    private String matchType;
    private String homeName;
    private String awayName;
    private String homeImage;
    private String awayImage;

    public NextMatchModel(String date, String matchType, String homeName, String awayName, String homeImage, String awayImage) {
        this.date = date;
        this.matchType = matchType;
        this.homeName = homeName;
        this.awayName = awayName;
        this.homeImage = homeImage;
        this.awayImage = awayImage;
    }

    public String getDate() {
        return date;
    }

    public String getMatchType() {
        return matchType;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public String getHomeImage() {
        return homeImage;
    }

    public String getAwayImage() {
        return awayImage;
    }
}
