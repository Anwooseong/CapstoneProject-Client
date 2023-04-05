package com.example.capstoneproject.activity;

import android.widget.TextView;

public class Frame {
    TextView[] scores = new TextView[3];
    TextView frameScore;

    public TextView getFrameScore() {
        return frameScore;
    }

    public void setFrameScore(TextView frameScore) {
        this.frameScore = frameScore;
    }
}
