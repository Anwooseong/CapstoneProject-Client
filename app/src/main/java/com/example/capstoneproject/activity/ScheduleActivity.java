package com.example.capstoneproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capstoneproject.R;
import com.example.capstoneproject.common.DateDiff;
import com.example.capstoneproject.data.game.GameService;
import com.example.capstoneproject.data.game.request.CheckSocketActiveRequest;
import com.example.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.example.capstoneproject.data.game.response.ChatRoomDTO;
import com.example.capstoneproject.data.game.response.CheckSocketActiveResult;
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
import com.example.capstoneproject.data.match.response.plan.GetDetailMatchResultDetail;
import com.example.capstoneproject.data.push.PushService;
import com.example.capstoneproject.data.push.request.PostCancelMatchReq;
import com.example.capstoneproject.view.CheckSocketActiveView;
import com.example.capstoneproject.view.GetDetailMatchView;
import com.example.capstoneproject.view.PostGameView;

import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements GetDetailMatchView, CheckSocketActiveView {

    private TextView date;
    private TextView homeText, homeHighScore, homeAvgScore, homeGameCount, homeWinCount, homeLoseCount;
    private TextView awayText, awayHighScore, awayAvgScore, awayGameCount, awayWinCount, awayLoseCount;
    private TextView matchCode, remainTime;
    private ImageView homeImageUrl, awayImageUrl, backBtn;
    private AppCompatButton startBtn, cancelBtn;
    private int matchIdx;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Intent intent = getIntent();
        Log.d("matchIdx", "Intent "+intent.getIntExtra("matchIdx", 0));
        matchIdx = intent.getIntExtra("matchIdx", 0);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MatchService matchService = new MatchService();
        matchService.setGetDetailMatchView(this);
        Log.d("matchIdx", "onStart: "+matchIdx);
        matchService.getDetailMatchResult(getJwt(), matchIdx);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 방장은 매칭방파기, 나머지 인원들은 매칭방 매치 취소
            }
        });
        // 매칭시작 버튼 누를 때
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 게임방 상태 WA인지 확인
                checkSocketActive();
            }
       });

        //매칭취소 버튼 누를 때
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMatch();
            }
        });
    }

    private void cancelMatch() {
        PushService pushService = new PushService();
        pushService.postCancelMatch(getJwt(), new PostCancelMatchReq(matchIdx));
    }

    //뷰 초기화
    private void init() {
        date = findViewById(R.id.next_match_date_tv);
        homeText = findViewById(R.id.home_match_schedule_tv);
        homeHighScore = findViewById(R.id.home_score_tv);
        homeAvgScore = findViewById(R.id.home_average_tv);
        homeGameCount = findViewById(R.id.home_game_tv);
        homeWinCount = findViewById(R.id.home_win_tv);
        homeLoseCount = findViewById(R.id.home_lose_tv);

        awayText = findViewById(R.id.away_match_schedule_tv);
        awayHighScore = findViewById(R.id.away_score_tv);
        awayAvgScore = findViewById(R.id.away_average_tv);
        awayGameCount = findViewById(R.id.away_game_tv);
        awayWinCount = findViewById(R.id.away_win_tv);
        awayLoseCount = findViewById(R.id.away_lose_tv);

        matchCode = findViewById(R.id.match_code_tv);
        remainTime = findViewById(R.id.match_remain_time_tv);

        homeImageUrl = findViewById(R.id.home_match_schedule_iv);
        awayImageUrl = findViewById(R.id.away_match_schedule_iv);

        startBtn = findViewById(R.id.match_start_btn);
        cancelBtn = findViewById(R.id.match_cancel_btn);
        backBtn = findViewById(R.id.match_schedule_back_btn);
    }

    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }
    private void checkSocketActive(){
        GameService gameService = new GameService();
        gameService.setCheckSocketActiveView(this);
        gameService.checkSocketActive(new CheckSocketActiveRequest(matchIdx));
    }
    @Override
    public void onDetailMatchSuccess(GetDetailMatchResponse resp) {
        date.setText(resp.getResult().getGameTime());
        List<GetDetailMatchResultDetail> getDetailResult = resp.getResult().getGetDetailMatchResultDetails();

        RequestOptions requestOptions = RequestOptions.skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        if (getDetailResult.size() == 1) {
            if (getDetailResult.get(0).getImageUrl() == " ") {
                homeImageUrl.setImageResource(R.drawable.default_profile);
                awayImageUrl.setImageResource(R.drawable.default_profile);
            } else {
                Glide.with(this).load(getDetailResult.get(0).getImageUrl())
                        .apply(requestOptions)
                        .into(homeImageUrl);
                awayImageUrl.setImageResource(R.drawable.default_profile);
            }
        }else{
            if (getDetailResult.get(0).getImageUrl() == " ") {
                homeImageUrl.setImageResource(R.drawable.default_profile);
            }else{
                Glide.with(this).load(getDetailResult.get(0).getImageUrl())
                        .apply(requestOptions)
                        .into(homeImageUrl);
            }
            if (getDetailResult.get(1).getImageUrl() == " ") {
                awayImageUrl.setImageResource(R.drawable.default_profile);
            } else {
                Glide.with(this).load(getDetailResult.get(1).getImageUrl())
                        .apply(requestOptions)
                        .into(awayImageUrl);
            }
        }
        homeImageUrl.setClipToOutline(true);
        awayImageUrl.setClipToOutline(true);

        homeText.setText(getDetailResult.get(0).getNickName());
        homeHighScore.setText(""+getDetailResult.get(0).getHighScore());
        homeAvgScore.setText(""+getDetailResult.get(0).getAvgScore());
        homeGameCount.setText("" + getDetailResult.get(0).getGameCount());
        homeWinCount.setText("" + getDetailResult.get(0).getWinCount());
        homeLoseCount.setText("" + getDetailResult.get(0).getLoseCount());
        if (getDetailResult.size() == 1) {
            awayText.setText("미정");
            awayHighScore.setText("-");
            awayAvgScore.setText("-");
            awayGameCount.setText("-");
            awayWinCount.setText("-");
            awayLoseCount.setText("-");
        } else {
            awayText.setText(getDetailResult.get(1).getNickName());
            awayHighScore.setText(""+getDetailResult.get(1).getHighScore());
            awayAvgScore.setText(""+getDetailResult.get(1).getAvgScore());
            awayGameCount.setText(""+getDetailResult.get(1).getGameCount());
            awayWinCount.setText(""+getDetailResult.get(1).getWinCount());
            awayLoseCount.setText(""+getDetailResult.get(1).getLoseCount());
        }
        matchCode.setText("" + resp.getResult().getMatchCode());

        DateDiff dateDiff = new DateDiff();
        String gameTime = resp.getResult().getGameTime();
        int hour, min;
        String[] getSplitDate = gameTime.split(" ");
        if (getSplitDate[1].equals("오후")) {
            String[] getSplitTime = getSplitDate[2].split(":");
            hour = Integer.valueOf(getSplitTime[0]) + 12;
            min = Integer.valueOf(getSplitTime[1]);
        } else {
            String[] getSplitTime = getSplitDate[2].split(":");
            hour = Integer.valueOf(getSplitTime[0]);
            min = Integer.valueOf(getSplitTime[1]);
        }
        String[] getDetailSplitDate = getSplitDate[0].split("-");
        countDownTimer = new CountDownTimer(200000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainTime.setText(dateDiff.getTime(Integer.valueOf(getDetailSplitDate[0]), Integer.valueOf(getDetailSplitDate[1]), Integer.valueOf(getDetailSplitDate[2]), hour, min));
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }


    @Override
    public void onDetailMatchFailure() {

    }

    // 매칭방 소켓활성화 여부 확인
    @Override
    public void onCheckSocketActiveViewSuccess(CheckSocketActiveResult result) {
        if(result.getStatus().equals("WA")){
            //TODO 게임시작
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            Log.d("TAG", "matchIdx test: "+matchIdx);
            intent.putExtra("matchIdx", matchIdx);
            intent.putExtra("matchCode",matchCode.getText());
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"활성화된 게임방이 아닙니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckSocketActiveViewFailure() {

    }
}
