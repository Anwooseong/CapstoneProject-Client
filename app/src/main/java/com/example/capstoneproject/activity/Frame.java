package com.example.capstoneproject.activity;

import android.widget.TextView;

public class Frame {
    TextView frameCount;
    TextView firstScore;
    TextView secondScore;
    TextView[] scores = new TextView[3];
    TextView thirdScore;
    TextView frameScore;

    public TextView getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(TextView frameCount) {
        this.frameCount = frameCount;
    }

    public TextView getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(TextView firstScore) {
        this.firstScore = firstScore;
    }

    public TextView getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(TextView secondScore) {
        this.secondScore = secondScore;
    }

    public TextView getThirdScore() {
        return thirdScore;
    }

    public void setThirdScore(TextView thirdScore) {
        this.thirdScore = thirdScore;
    }

    public TextView getFrameScore() {
        return frameScore;
    }

    public void setFrameScore(TextView frameScore) {
        this.frameScore = frameScore;
    }
}
