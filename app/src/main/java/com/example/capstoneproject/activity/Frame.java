package com.example.capstoneproject.activity;

import android.widget.TextView;

/**
 * 게임진행 시 볼링 점수판 XML에 표현할 값들을 저장한 객체
 */
public class Frame {
    TextView frameCount;
    TextView[] scores = new TextView[3];
    TextView frameScore;

    public TextView getFrameScore() {
        return frameScore;
    }

    public void setFrameScore(TextView frameScore) {
        this.frameScore = frameScore;
    }
}
