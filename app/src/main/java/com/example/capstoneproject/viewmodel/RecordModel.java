package com.example.capstoneproject.viewmodel;

public class RecordModel {

    private String date;
    private String type;
    private String result;
    private String homeTeam;
    private String homeScore;
    private String awayTeam;
    private String awayScore;

    public RecordModel(String date, String type, String result, String homeTeam, String homeScore, String awayTeam, String awayScore) {
        this.date = date;
        this.type = type;
        this.result = result;
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getResult() {
        return result;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getAwayScore() {
        return awayScore;
    }
}
